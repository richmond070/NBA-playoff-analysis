from airflow.decorators import dag, task
from airflow.providers.snowflake.hooks.snowflake import SnowflakeHook
from datetime import datetime
import requests, time
import pandas as pd
from bs4 import BeautifulSoup
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service

YEARS = list(range(2023, 2026))

def write_to_snowflake(df: pd.DataFrame, table_name: str):
    """Utility: write a DataFrame into Snowflake table"""
    hook = SnowflakeHook(snowflake_conn_id="snowflake_default")
    engine = hook.get_sqlalchemy_engine()
    df.to_sql(
        name=table_name,
        con=engine,
        if_exists="replace", 
        index=False
    )

@dag(
    dag_id="nba_scraping_to_snowflake",
    schedule="@once",
    start_date=datetime(2025, 8, 21),
    catchup=False,
    description="Scrapes NBA data and pushes directly into Snowflake",
)
def nba_scraping_pipeline():

    @task
    def scrape_team_ratings():
        team_ratings_url = "https://www.basketball-reference.com/leagues/NBA_{}_ratings.html"
        dfs = []
        for year in YEARS:
            url = team_ratings_url.format(year)
            data = requests.get(url)
            soup = BeautifulSoup(data.text, "html.parser")
            soup.find("tr", class_="over_header").decompose()
            ratings_table = soup.find(id="ratings")
            rating = pd.read_html(str(ratings_table))[0]
            rating["Year"] = year
            dfs.append(rating)
        ratings = pd.concat(dfs)
        write_to_snowflake(ratings, "NBA_TEAM_RATINGS")
        return "NBA_TEAM_RATINGS"

    @task
    def scrape_player_stats():
        player_stats_url = "https://www.basketball-reference.com/leagues/NBA_{}_per_game.html"
        dfs = []
        service = Service(ChromeDriverManager().install())
        driver = webdriver.Chrome(service=service)

        for year in YEARS:
            url = player_stats_url.format(year)
            driver.get(url)
            driver.execute_script("window.scrollTo(1,10000)")
            time.sleep(2)
            html = driver.page_source
            soup = BeautifulSoup(html, "html.parser")
            soup.find("tr", class_="thead").decompose()
            player_table = soup.find(id="per_game_stats")
            player = pd.read_html(str(player_table))[0]
            player["Year"] = year
            dfs.append(player)

        driver.quit()
        players = pd.concat(dfs)
        write_to_snowflake(players, "NBA_PLAYER_STATS")
        return "NBA_PLAYER_STATS"

    @task
    def scrape_team_stats():
        dfs_divs, dfs_confs = [], []
        for year in YEARS:
            team_stats_url = f"https://www.basketball-reference.com/leagues/NBA_{year}.html"
            data = requests.get(team_stats_url)

            # Division standings
            for div_id, col_name in [("divs_standings_E", "Eastern Conference"),
                                     ("divs_standings_W", "Western Conference")]:
                s = BeautifulSoup(data.text, "html.parser")
                s.find("tr", class_="thead").decompose()
                table = s.find(id=div_id)
                team = pd.read_html(str(table))[0]
                team["Year"] = year
                team["Team"] = team[col_name]
                del team[col_name]
                dfs_divs.append(team)

            # Conference standings
            for conf_id, col_name in [("confs_standings_E", "Eastern Conference"),
                                      ("confs_standings_W", "Western Conference")]:
                s = BeautifulSoup(data.text, "html.parser")
                s.find("tr", class_="thead").decompose()
                table = s.find(id=conf_id)
                team = pd.read_html(str(table))[0]
                team["Year"] = year
                team["Team"] = team[col_name]
                del team[col_name]
                dfs_confs.append(team)

        divs = pd.concat(dfs_divs)
        confs = pd.concat(dfs_confs)
        write_to_snowflake(divs, "NBA_TEAMS_DIVS")
        write_to_snowflake(confs, "NBA_TEAMS_CONFS")
        return ["NBA_TEAMS_DIVS", "NBA_TEAMS_CONFS"]

    ratings = scrape_team_ratings()
    players = scrape_player_stats()
    teams = scrape_team_stats()

dag = nba_scraping_pipeline()
