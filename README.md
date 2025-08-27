NBA Playoff Analysis Project
1. Project Overview

This project analyzes NBA playoff data, originally designed with Postgres + Spark for storage and transformations, and now updated to use Airflow + Snowflake + dbt for modern orchestration, warehousing, and analytics.

The goal is to:

Scrape NBA playoff data

Store raw and transformed datasets

Apply business rules to enrich insights

Provide structured data for downstream analysis and visualization

2. Aim

The aim of this project is to design and implement a data-driven system for extracting, storing, and analyzing historical NBA statistics to predict potential outcomes of the 2024–2025 NBA playoffs.

Rather than employing machine learning, this project relies on comparative and statistical analysis of past seasons (2022–2023 and 2023–2024) to identify key performance patterns and trends that can guide predictive insights.

3. Architecture Evolution
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

4. Data Dictionary
Tables

games

game_id: Unique identifier

season: Season year

date: Game date

home_team, away_team: Team identifiers

home_score, away_score: Points scored

round: Playoff round

players

player_id: Unique identifier

team_id: Associated team

player_name: Full name

position: Role on team

stats

stat_id: Unique identifier

game_id: Linked to games

player_id: Linked to players

points, rebounds, assists, … : Performance metrics

5. Relational Model

Relationships:

games ⟷ stats (via game_id)

players ⟷ stats (via player_id)

teams ⟷ players (via team_id)

The model allows queries like:

Player performance in specific games

Team aggregates across playoff rounds

Historical comparisons between seasons

6. Transformations
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

7. Workflow
Original Workflow

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

8. Rules-Based Framework

Transformation rules:

Standardize player/team names

Remove duplicates

Normalize date formats

Business rules:

Calculate playoff averages per player

Identify top performers per round

Derive team win/loss ratios

9. Evaluation Metrics

Data Quality

Missing value checks

Referential integrity (foreign key consistency)

Duplicate detection

Performance

Query execution times in Snowflake

Airflow DAG runtime efficiency

10. Project Structure
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

11. Future Enhancements

Add API-based scraping to reduce Selenium dependency

Expand dbt models to include advanced metrics (PER, win shares)

Automate CI/CD for dbt with GitHub Actions

Visualize in Tableau or Power BI with live Snowflake connection


Visualize in Tableau or Power BI with live Snowflake connection
