# NBA Playoff Analysis Project

<img width="1683" height="965" alt="architecture diagram" src="https://github.com/user-attachments/assets/b4d57fdc-45cc-4dee-8c2a-cff6d4968ddc" />



## Project Overview

This project analyzes NBA playoff data, originally designed with Postgres + Spark for storage and transformations, and now updated to use Airflow + Snowflake + dbt for modern orchestration, warehousing, and analytics.

The project does the following:

1. Scrape NBA playoff data

2. Store raw and transformed datasets

3. Apply business rules to enrich insights

4. Provide structured data for downstream analysis and visualization

## Aim

The aim of this project is to design and implement a data-driven system for extracting, storing, and analyzing historical NBA statistics to predict potential outcomes of the 2024–2025 NBA playoffs.

Rather than employing machine learning, this project relies on comparative and statistical analysis of past seasons (2022–2023 and 2023–2024) to identify key performance patterns and trends that can guide predictive insights.

## Architecture Evolution

### Original Design

- Data Storage: Postgres database

- Transformations: PySpark (ETL jobs run locally or in cluster mode)

- Workflow: Manual / script-based

### Updated Stack

- Orchestration: Apache Airflow (Astro Runtime)

- Data Warehouse: Snowflake (scalable cloud-native storage & compute)

- Transformations: dbt (SQL-based modeling, version-controlled)

- Integration:

  apache-airflow-providers-snowflake for Airflow-Snowflake connections

  Snowflake python-connector for programmatic writes

  Astronomer Cosmos for seamless dbt-Airflow integration

This update enables:

Automated scraping + loading pipelines, Cloud-native, elastic scaling, CI/CD-friendly transformations, Clear separation of staging schema vs analytics schema

## Data Dictionary

For data dictionary see the [dictionary](/Dictionary.md)

## Transformations

### Original Design (ScalaSpark)

- Load raw CSVs into Spark DataFrames

- Clean missing values, normalize formats

- Join across datasets (rating, players, conference)

- Write transformed results back to Postgres

### Updated Stack (dbt + Snowflake)

1. Staging schema: Raw scraped data lands in Snowflake via Airflow DAG (scrape_data → stage_data).

2. dbt models:

   stg_games.sql, stg_players.sql, stg_stats.sql → clean staging tables

   fct_performance.sql → fact table for player performance

   dim_teams.sql, dim_players.sql → dimensions for analysis

3. Analytics schema: Final dbt models materialized in Snowflake for BI tools.

## Workflow

### Original Workflow

- Manually run scraping script

- Save CSVs locally

- Load into Postgres

- Run PySpark ETL jobs

### Updated Workflow (Airflow)

- scrape_data task: Uses requests + BeautifulSoup + Selenium to pull playoff stats.

- stage_data task: Pushes raw data directly into Snowflake (via Airflow-Snowflake connection).

- dbt_run task: Executes dbt models with Astronomer Cosmos.

- dbt_test task: Validates models for data quality.

- Downstream analysis: BI / notebooks connect to Snowflake.

## Rules-Based Framework

### Transformation rules:

- Standardize player/team names

- Remove duplicates

- Normalize date formats

### Business rules:

- Calculate playoff averages per player

- Identify top performers per round

- Derive team win/loss ratios

## Evaluation Metrics

- Data Quality

- Missing value checks

- Referential integrity (foreign key consistency)

- Duplicate detection

## Performance

- Query execution times in Snowflake

- Airflow DAG runtime efficiency

## Project Structure

```
NBA-playoff-analysis
├── Airflow
│   ├── dags
│   │   └──  webscraping_automation.py
│   ├── include
│   │   └── webscraping.py
│   └── requirements.txt
├── dbt
│   ├── models
│   │   ├── staging
│   │   ├── marts
│   │   └── tests
│   └── dbt_project.yml
└── docs
    └── README.md

```

## Future Enhancements

- Add API-based scraping to reduce Selenium dependency

- Expand dbt models to include advanced metrics (PER, win shares)

- Automate CI/CD for dbt with GitHub Actions

- Visualize inPower BI with live Snowflake connection (Direct Connect).
