# NBA Playoff Analysis Project

## Project Overview

This project analyzes NBA playoff data, originally designed with Postgres + Spark for storage and transformations, and now updated to use Airflow + Snowflake + dbt for modern orchestration, warehousing, and analytics.

The goal is to:

Scrape NBA playoff data

Store raw and transformed datasets

Apply business rules to enrich insights

Provide structured data for downstream analysis and visualization

## Aim

The aim of this project is to design and implement a data-driven system for extracting, storing, and analyzing historical NBA statistics to predict potential outcomes of the 2024–2025 NBA playoffs.

Rather than employing machine learning, this project relies on comparative and statistical analysis of past seasons (2022–2023 and 2023–2024) to identify key performance patterns and trends that can guide predictive insights.

## Architecture Evolution
Original Design

Data Storage: Postgres database

Transformations: PySpark (ETL jobs run locally or in cluster mode)

Workflow: Manual / script-based

Updated Stack

Orchestration: Apache Airflow (Astro Runtime)

Data Warehouse: Snowflake (scalable cloud-native storage & compute)

Transformations: dbt (SQL-based modeling, version-controlled)

Integration:

apache-airflow-providers-snowflake for Airflow-Snowflake connections

Snowflake python-connector for programmatic writes

Astronomer Cosmos for seamless dbt-Airflow integration

This update enables:

Automated scraping + loading pipelines

Cloud-native, elastic scaling

CI/CD-friendly transformations

Clear separation of staging schema vs analytics schema

## Data Dictionary
To assist the team in understanding the data the following definitions and brief descriptions for each data table are provided:

Player Table
Rk (Rank): The player’s ranking position within the dataset based on sorting criteria.

Player: The NBA athlete’s name.

Age: The player’s age during the season.

Team: The NBA team the player represents.

Pos (Position): On-court role (e.g., Guard, Forward, Center).

G (Games): Total games participated in.

GS (Games Started): Number of games where the player started.

MP (Minutes Played): Total minutes on court.

FG (Field Goals Made): Shots made from the field (excluding free throws).

FGA (Field Goal Attempts): Total field goal attempts.

FG% (Field Goal Percentage): Percentage of successful field goals.

3P (Three-Point Field Goals Made): Successful three-point shots.

3PA (Three-Point Field Goal Attempts): Total three-point attempts.

3P% (Three-Point Percentage): Success rate for three-point shots.

2P (Two-Point Field Goals Made): Successful two-point shots.

2PA (Two-Point Field Goal Attempts): Total two-point attempts.

2P% (Two-Point Percentage): Success rate for two-point shots.

eFG% (Effective Field Goal Percentage): Shooting percentage that accounts for the extra value of three-point shots.

FT (Free Throws Made): Free throws successfully made.

FTA (Free Throw Attempts): Total free throw attempts.

FT% (Free Throw Percentage): Percentage of successful free throws.

ORB (Offensive Rebounds): Rebounds collected on the offensive end.

DRB (Defensive Rebounds): Rebounds collected on the defensive end.

TRB (Total Rebounds): Combined offensive and defensive rebounds.

AST (Assists): Passes leading directly to a score by a teammate.

STL (Steals): Times the player took the ball away from the opposing team.

BLK (Blocks): Successful deflections of opponent shot attempts.

TOV (Turnovers): Times the player lost ball possession.

PF (Personal Fouls): Fouls committed by the player.

PTS (Points): Total points scored in the season.

Awards: Awards or accolades received.

Year: The season the statistics represent.

Team Ratings Table
Rk (Rank): The ranking position of the team.

Team: The name of the NBA team.

Conf (Conference): The conference in which the team competes (Eastern or Western).

Div (Division): The division within the conference.

W (Wins): Games won during the season.

L (Losses): Games lost.

W/L% (Winning Percentage): Wins divided by total games.

MOV (Margin of Victory): Average point difference per game.

ORtg (Offensive Rating): Points scored per 100 possessions.

DRtg (Defensive Rating): Points allowed per 100 possessions.

NRtg (Net Rating): Difference between offensive and defensive ratings.

MOV/A (Adjusted Margin of Victory): Margin adjusted for opponent strength and other factors.

ORtg/A (Adjusted Offensive Rating): Offensive rating refined by context factors.

DRtg/A (Adjusted Defensive Rating): Defensive rating refined by context factors.

NRtg/A (Adjusted Net Rating): Adjusted overall performance metric based on offense and defense.

Year: The season associated with the ratings.

Team Conference Standings Table
W (Wins): Total conference wins.

L (Losses): Total conference losses.

W/L% (Winning Percentage): Win ratio based on conference games.

GB (Games Behind): The number of games behind the conference leader.

PS/G (Points Scored Per Game): Average points scored per game.

PA/G (Points Allowed Per Game): Average points conceded per game.

SRS (Simple Rating System): Cumulative metric combining point differential and strength of schedule.

Year: The season or year of the standings.

Team: The team’s name as listed in conference standings.

Team Division Standings Table
Rk (Rank): The division ranking of the team (if provided as the first column).

W (Wins): Wins accumulated in division matchups.

L (Losses): Losses incurred in division matchups.

W/L% (Winning Percentage): Win ratio derived from division games.

GB (Games Behind): Games behind the division leader.

PS/G (Points Scored Per Game): Average points scored in division games.

PA/G (Points Allowed Per Game): Average points conceded in division games.

SRS (Simple Rating System): Metric combining the scoring margin and schedule strength for division games.

Year: The season or year associated with these standings.

Team: The team’s name from the division standings.

## Transformations
Original Design (PySpark)

Load raw CSVs into Spark DataFrames

Clean missing values, normalize formats

Join across datasets (games, players, stats)

Write transformed results back to Postgres

Updated Stack (dbt + Snowflake)

Staging schema: Raw scraped data lands in Snowflake via Airflow DAG (scrape_data → stage_data).

dbt models:

stg_games.sql, stg_players.sql, stg_stats.sql → clean staging tables

fct_performance.sql → fact table for player performance

dim_teams.sql, dim_players.sql → dimensions for analysis

Analytics schema: Final dbt models materialized in Snowflake for BI tools.

## Workflow
### Original Workflow

Manually run scraping script

Save CSVs locally

Load into Postgres

Run PySpark ETL jobs

Updated Workflow (Airflow)

scrape_data task: Uses requests + BeautifulSoup + Selenium to pull playoff stats.

stage_data task: Pushes raw data directly into Snowflake (via Airflow-Snowflake connection).

dbt_run task: Executes dbt models with Astronomer Cosmos.

dbt_test task: Validates models for data quality.

Downstream analysis: BI / notebooks connect to Snowflake.

## Rules-Based Framework

Transformation rules:

Standardize player/team names

Remove duplicates

Normalize date formats

Business rules:

Calculate playoff averages per player

Identify top performers per round

Derive team win/loss ratios

## Evaluation Metrics

Data Quality

Missing value checks

Referential integrity (foreign key consistency)

Duplicate detection

Performance

Query execution times in Snowflake

Airflow DAG runtime efficiency

## Project Structure
NBA-playoff-analysis/
│
├── Airflow/
│   ├── dags/
│   │   └── webscraping_automation.py   # Airflow DAG
│   ├── include/
│   │   └── webscraping.py              # scraping logic
│   └── requirements.txt                # dependencies
│
├── dbt/
│   ├── models/
│   │   ├── staging/
│   │   ├── marts/
│   │   └── tests/
│   └── dbt_project.yml
│
└── docs/
    └── README.md

## Future Enhancements

Add API-based scraping to reduce Selenium dependency

Expand dbt models to include advanced metrics (PER, win shares)

Automate CI/CD for dbt with GitHub Actions

Visualize in Tableau or Power BI with live Snowflake connection


Visualize in Tableau or Power BI with live Snowflake connection
