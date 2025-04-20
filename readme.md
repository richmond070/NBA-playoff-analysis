# Web Data Extraction and Analysis to Predict NBA Playoff Outcomes

## Project Architecture

![project architecture](https://github.com/user-attachments/assets/2cd340b6-1a34-4467-8a38-cafdd7a8be65)


## Aim

The aim of this project is to design and implement a data-driven system for extracting, storing, and analyzing historical NBA statistics to predict potential outcomes of the 2024-2025 NBA playoffs. Rather than employing machine learning, this project relies on comparative and statistical analysis of past seasons (2022-2023 and 2023-2024) to identify key performance patterns and trends that can guide predictive insights.

## Objectives

1. **Data Architecture**

- Design of a data architecture that shows the work flow of the `EtLT` data pipeline to get the data from source to destination for the analysis to be done.

2. **Data Extraction**

- Scrape structured data including team statistics, player performance metrics, win-loss records, and team ratings from https://www.basketball-reference.com using Python, BeautifulSoup/Selenium.

3. **Data Preprocessing**

- Using Spark (PySpark) to clean and standardize the scraped data.

4. **Data Storage**

- Create a PostgreSQL database schema tailored to efficiently store and query the extracted tables.

5. **Data Transformation**

-

6. **Trend and Pattern Analysis**

- Analyze the four datasets for historical performance patterns that have traditionally correlated with playoff success.

- Identify key performance indicators (KPIs) within the team and player rating metrics, as well as the standings information.

- Establish a set of rule-based decision rules based on the analysis.

7. **Rule-Based Prediction Framework**

- Apply the established decision rules to the 2024-2025 season's data to generate playoff outcome predictions using only the scraped datasets.

8. **Workflow Automation**

- Implement Apache Airflow to schedule and automate the data extraction, transformation and analysis processes for continuous updates during the season.

9. **Evaluation and Refinement**

- Compare the rule-based predictions with actual playoff outcomes, and refine the decision rules based solely on the four data sources.

## Data Dictionary

To assist the team in understanding the data the following definitions and brief descriptions for each data table are provided:

1. ### Player Table

- **Rk (Rank):** The player’s ranking position within the dataset based on sorting criteria.

- **Player:** The NBA athlete’s name.

- **Age:** The player’s age during the season.

- **Team:** The NBA team the player represents.

- **Pos (Position):** On-court role (e.g., Guard, Forward, Center).

- **G (Games):** Total games participated in.

- **GS (Games Started):** Number of games where the player started.

- **MP (Minutes Played):** Total minutes on court.

- **FG (Field Goals Made):** Shots made from the field (excluding free throws).

- **FGA (Field Goal Attempts):** Total field goal attempts.

- **FG% (Field Goal Percentage):** Percentage of successful field goals.

- **3P (Three-Point Field Goals Made):** Successful three-point shots.

- **3PA (Three-Point Field Goal Attempts):** Total three-point attempts.

- **3P% (Three-Point Percentage):** Success rate for three-point shots.

- **2P (Two-Point Field Goals Made):** Successful two-point shots.

- **2PA (Two-Point Field Goal Attempts):** Total two-point attempts.

- **2P% (Two-Point Percentage):** Success rate for two-point shots.

- **eFG% (Effective Field Goal Percentage):** Shooting percentage that accounts for the extra value of three-point shots.

- **FT (Free Throws Made):** Free throws successfully made.

- **FTA (Free Throw Attempts):** Total free throw attempts.

- **FT% (Free Throw Percentage):** Percentage of successful free throws.

- **ORB (Offensive Rebounds):** Rebounds collected on the offensive end.

- **DRB (Defensive Rebounds):** Rebounds collected on the defensive end.

- **TRB (Total Rebounds):** Combined offensive and defensive rebounds.

- **AST (Assists):** Passes leading directly to a score by a teammate.

- **STL (Steals):** Times the player took the ball away from the opposing team.

- **BLK (Blocks):** Successful deflections of opponent shot attempts.

- **TOV (Turnovers):** Times the player lost ball possession.

- **PF (Personal Fouls):** Fouls committed by the player.

- **PTS (Points):** Total points scored in the season.

- **Awards:** Awards or accolades received.

- **Year:** The season the statistics represent.

### 2. Team Ratings Table

- **Rk (Rank):** The ranking position of the team.

- **Team:** The name of the NBA team.

- **Conf (Conference):** The conference in which the team competes (Eastern or Western).

- **Div (Division):** The division within the conference.

- **W (Wins):** Games won during the season.

- **L (Losses):** Games lost.

- **W/L% (Winning Percentage):** Wins divided by total games.

- **MOV (Margin of Victory):** Average point difference per game.

- **ORtg (Offensive Rating):** Points scored per 100 possessions.

- **DRtg (Defensive Rating):** Points allowed per 100 possessions.

- **NRtg (Net Rating):** Difference between offensive and defensive ratings.

- **MOV/A (Adjusted Margin of Victory):** Margin adjusted for opponent strength and other factors.

- **ORtg/A (Adjusted Offensive Rating):** Offensive rating refined by context factors.

- **DRtg/A (Adjusted Defensive Rating):** Defensive rating refined by context factors.

- **NRtg/A (Adjusted Net Rating):** Adjusted overall performance metric based on offense and defense.

- **Year:** The season associated with the ratings.

### 3. Team Conference Standings Table

- **W (Wins):** Total conference wins.

- **L (Losses):** Total conference losses.

- **W/L% (Winning Percentage):** Win ratio based on conference games.

- **GB (Games Behind):** The number of games behind the conference leader.

- **PS/G (Points Scored Per Game):** Average points scored per game.

- **PA/G (Points Allowed Per Game):** Average points conceded per game.

- **SRS (Simple Rating System):** Cumulative metric combining point differential and strength of schedule.

- **Year:** The season or year of the standings.

- **Team:** The team’s name as listed in conference standings.

### 4. Team Division Standings Table

- **Rk (Rank):** The division ranking of the team (if provided as the first column).

- **W (Wins):** Wins accumulated in division matchups.

- **L (Losses):** Losses incurred in division matchups.

- **W/L% (Winning Percentage):** Win ratio derived from division games.

- **GB (Games Behind):** Games behind the division leader.

- **PS/G (Points Scored Per Game):** Average points scored in division games.

- **PA/G (Points Allowed Per Game):** Average points conceded in division games.

- **SRS (Simple Rating System):** Metric combining the scoring margin and schedule strength for division games.

- **Year:** The season or year associated with these standings.

- **Team:** The team’s name from the division standings.

### Important:

Something to note on the data some teams in the teams _conf_standings have numbers on them “Indiana Pacers (4)_ and some have (\_)" on them “Milwaukee Bucks\_” as well. The number (4) represent the position in which the team got in on the playoffs (Indiana Pacers made the number 4 pick for the playoff position for the Eastern conference) and (\_) represent the teams that have made it into this playoff round for that season.

### Conference acrimony

- **W:** `Stands for Western Conference`
- **E:** `Stands for Eastern Conference`

### Division acrimony

- **NW:** `Northwest division`
- **SE:** `Southeastern division`
- **P:** `Pacific division`
- **SW:** `Southwest division`
- **C:** `Central division`

### Database Relationships

**Player Table ↔ Team Ratings Table**

_Relationship Type:_ `Many-to-One`

`Details:` Each record in the Player Table `representing an individual player’s season statistics` is associated with a single team. The Player Table should include a foreign key composed of `Team, Year` that references the primary key in the Team Ratings Table. This establishes that many players belong to one team for a given season.

**Team Ratings Table ↔ Team Conference Standings Table**

_Relationship_ `Type: One-to-One (or One-to-Few) per Season`

`Details:` Both tables represent team-level data for a specific season. They share the composite key `Team, Year`, making it possible to join each team’s efficiency and performance metrics `from the Team Ratings Table` with their overall conference performance metrics `from the Team Conference Standings Table`.

**Team Ratings Table ↔ Team Division Standings Table**

_Relationship Type:_ `One-to-One (or One-to-Few) per Season`

`Details:` Similarly, the Team Division Standings Table uses `Team, Year` as a key to record division-specific performance. Each team record in the Team Ratings Table will have a corresponding record in the Team Division Standings Table for the same season, allowing analysis of how team performance translates into their division ranking.

**Team Conference Standings Table ↔ Team Division Standings Table**

_Relationship Type:_ `One-to-One (per Team per Season)`

`Details:` Although these two tables provide different perspectives—the conference versus the division view—they both record standings for the same team and season. They can be joined on `Team, Year) to compare and analyze how a team’s performance holds across both its conference and its division.


## Data Transformation

This project utilizes Apache Spark for data transformation. The following steps outline the methodology used:

### Step 1: Spark Session Creation
A Spark session was created using the SparkSession.builder API, configuring the application name and other necessary settings.

### Step 2: Schema Definition
A predefined schema was defined using the ```StructType``` and ```StructField``` APIs, specifying the column names and data types.

### Step 3: Data Loading
The data was loaded into a Spark DataFrame using the ```spark.read``` API, specifying the data source and other necessary options.

### Step 4: Applying Schema
The predefined schema was applied to the loaded data using the spark.read.schema API, ensuring data consistency and correctness.

### Step 5: Transformations
The following transformations were applied to the data:

- Renaming Columns: Column names were renamed using the ```.withColumnRenamed``` API to ensure clarity and consistency.
- Handling Null Values: Null values in a specific column were filled with the ratio of two other columns that it is a result of, using the when and otherwise APIs.
- Dropping Unnecessary Columns: A column that was not needed for further analysis was dropped using the drop API.
- Filtering Unwanted Rows: Unwanted rows were filtered out using the filter API, ensuring data quality and relevance.

### Step 6: Writing to Postgres
The transformed data was written to a Postgres database using the ```write.jdbc(:postgresql://...)``` API, specifying the database connection properties and table name.

### Step 7: Final Output
The data is now stored in the Postgres database, ready for further analysis.
