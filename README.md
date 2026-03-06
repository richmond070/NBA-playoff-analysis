# NBA Playoff Analysis Project


## Project Overview

This project uses historical NBA data (2022–2024) scraped from web to analyze team, player, and conference performance and identify patterns that inform 2024–2025 playoff expectations, using descriptive and comparative analytics in a three-page Power BI dashboard.


## Technologies

- Webscrapping: Python

- Data Warehouse: Snowflake

- Data Transformations: DBT core

- Analysis and Visualization: PowerBI


## Methodology

Raw data is first scraped from the web using a Python library designed for extracting information from online sources. Once collected, these raw datasets are loaded directly into a Snowflake staging schema, where they are stored in their original or minimally processed form. 
From there, DBT (Data Build Tool) connects to the staging schema and applies a series of transformations such as cleaning, standardizing, and modeling the data. The transformed datasets are then loaded into a destination schema within Snowflake that is structured and optimized for analytical use.
Finally, Power BI connects directly to this destination schema to perform data analysis and create interactive dashboards and visualizations for reporting and insights.

```
  Source Data                       Storage/Transformation            Visualization
  ──────────                        ──────────────────────            ─────────────

  players table.html        ─┐                                            
  team_ratings table.html   ─┼──>    [Raw]     ──>   [Mart]    ──>    Dashboard/Reports
  team_stats table.html     ─┘     (Landing)         (Final)            (Output)

```

## Project Structure
The repository is organized into several directories that represent the different stages of the data pipeline, from data collection and transformation to analysis and reporting.

- analysis report/ contains the final analytical outputs of the project, including the Power BI dashboard (.pbix) and exported PDF reports summarizing the NBA playoff analysis.

- dbt_NBA/ contains the dbt project used for transforming raw data into analytics-ready datasets.

- The models/ directory is organized into a Bronze–Gold architecture, where the Bronze layer contains staging models that clean and standardize raw data (stg_ratings.sql, stg_teams_conf_standings.sql, stg_players.sql), while the Gold layer contains final analytical models (team_ratings.sql, conference_standing.sql, nba_players.sql). 
Each model has corresponding YAML files that define documentation, tests, and metadata.

Other folders such as macros/, seeds/, snapshots/, and tests/ support reusable SQL logic, seed data loading, historical tracking, and data quality testing.

- raw data/ stores the original datasets (CSV files) used in the project before transformation.

- webscrapping scripts/ contains the Python scripts used to scrape NBA data from the web, along with saved HTML snapshots of the source tables used during development.

- data dictionary.md documents the datasets, including column definitions and descriptions.

- README.md This is the root readme that provides an overview of the project, while .gitignore ensures unnecessary files are excluded from version control.


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
## Data Dictionary

See [dictionary](/Dictionary.md)