# NBA Playoff Analysis Project


## Project Overview

This project uses historical NBA data (2022–2024) scraped from web to analyze team, player, and conference performance and identify patterns that inform 2024–2025 playoff expectations, using descriptive and comparative analytics in a three-page Power BI dashboard.


## Technologies

- Webscrapping: Python

- Data Warehouse: Snowflake

- Data Transformations: DBT core

- Analysis and Visualization: PowerBI


## Methodology

Raw data is first scraped from the web using a Python library designed for extracting information from online sources. Once collected, these raw datasets are loaded directly into a Snowflake staging schema, where they are stored in their original or minimally processed form. From there, DBT (Data Build Tool) connects to the staging schema and applies a series of transformations such as cleaning, standardizing, and modeling the data. The transformed datasets are then loaded into a destination schema within Snowflake that is structured and optimized for analytical use. Finally, Power BI connects directly to this destination schema to perform data analysis and create interactive dashboards and visualizations for reporting and insights.

```
  Source Data                       Storage/Transformation            Visualization
  ──────────                        ──────────────────────            ─────────────

  players table.html        ─┐                                            
  team_ratings table.html   ─┼──>    [Raw]     ──>   [Mart]    ──>    Dashboard/Reports
  team_stats table.html     ─┘     (Landing)         (Final)            (Output)

```

## Data Dictionary

See [dictionary](/Dictionary.md)


## Project Structure

```
NBA-playoff-analysis
    |───analysis report
    |     ├───NBA Project PowerBI report.pbix
    |     ├───NBA Project PowerBI report.pdf
    |     └───NBA Project Report.pdf
    |
    |───dbt_NBA
    |     ├─── macros
    |     ├─── models
    |     |      ├─── bronze
    |     |      |      ├─── dot sql
    |     |      |      |      ├───stg_ratings.sql
    |     |      |      |      ├───stg_teams_conf_standings.sql
    |     |      |      |      └───stg_players.sql
    |     |      |      | 
    |     |      |      └─── dot yaml
    |     |      |             ├─── _stg_ratings.yml
    |     |      |             ├─── _stg_teams_conf_standings.yml
    |     |      |             ├─── _stg_players.yml
    |     |      |             └─── sources.yml
    |     |      |      
    |     |      └─── gold
    |     |             ├─── dot sql 
    |     |             |      ├─── team_ratings.sql
    |     |             |      ├─── conference standing.sql
    |     |             |      └─── nba_players.sql
    |     |             | 
    |     |             └─── dot yaml
    |     |                    ├─── _team_ratings.yml
    |     |                    ├─── _conference_standing.yml
    |     |                    └─── _nba_players.yml
    |     |                          
    |     ├─── seeds
    |     ├─── snapshots
    |     ├─── tests
    |     ├─── .gitignore
    |     ├─── dbt_project.yml
    |     ├─── packages.yml
    |     └─── readme.md
    |
    ├───raw data
    |      ├───players.csv
    |      ├───ratings.csv
    |      ├───teams_conf_standings.csv
    |      ├───teams_division_standings.csv
    ├───webscrapping scripts
    |      ├───web snapshots
    |      |   ├───player table
    |      |   ├───team_ratings table
    |      |   └───team_stats table
    |      └───webscrapping.py
    ├───.gitignore
    ├─── data dictionary.md
    └─── README.md
```