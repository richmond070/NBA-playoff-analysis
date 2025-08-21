from airflow.decorators import dag,task
from include.webscrapping_automation import #function
from datetime import timedelta, datetime


@dag(
    dag_id = "for_nba_playoff_season",
    description = ("this dag scrapes data from a website,"
                   "stages it into a snowflake schema,"
                   "dbt transform it and stores in a separate schema for analysis"
    ),
    start_date = datetime(2025, 8, 27),
    schedule = "@once"
    catchup = False,
    default_args = {"owner" : "airflow", "retries" : 1, "retry_delay" = timedelta(minutes=5)}

)

def nba_playoff_pipeline():
    
    @task
    def scrape_data():
       return scrape_data


    @task
    def stage_data():
        return stage_data

    @task()
    def transform_data():
       return transform_data

    @task
    def load_data():
        return load_data


nba_playoff_pipeline()



