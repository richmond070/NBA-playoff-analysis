import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, when}
import org.apache.spark.sql.types.{FloatType, IntegerType, StringType, StructField, StructType}

object playoff {
  def main(args:Array[String]):Unit={

    //creating a spark session

    val NBA_ = SparkSession.builder()
      .appName("nbaspark_")
      .master("local[*]")
      .getOrCreate()


    //players table

    val schema_players = StructType(List(StructField("_c0", IntegerType),                                                     //schema definition
      StructField("Rk", IntegerType), StructField("Player", StringType),
      StructField("Age", IntegerType), StructField("Team", StringType),
      StructField("Pos", StringType), StructField("G", IntegerType),
      StructField("GS", IntegerType), StructField("MP", FloatType),
      StructField("FG", FloatType), StructField("FGA", FloatType),
      StructField("FG%", FloatType), StructField("3P", FloatType),
      StructField("3PA", FloatType), StructField("3P%", FloatType),
      StructField("2P", FloatType), StructField("2PA", FloatType),
      StructField("2P%", FloatType), StructField("eFG%", FloatType),
      StructField("FT", FloatType), StructField("FTA", FloatType),
      StructField("FT%", FloatType), StructField("ORB", FloatType),
      StructField("DRB", FloatType), StructField("TRB", FloatType), StructField("AST", FloatType),
      StructField("STL", FloatType), StructField("BLK", FloatType), StructField("TOV", FloatType),
      StructField("PF", FloatType), StructField("PTS", FloatType), StructField("Awards", StringType), StructField("Year", IntegerType)))


    val First = NBA_.read                                                                               //reading the data
      .format("csv")
      .option("header", true)
      .schema(schema_players)
      .option("path", "C:/Users/LATEEF/Downloads/NBAproject/players.csv")
      .load()


    val replaced_null = First.withColumn("2P%", when(col("2P%").isNull, col("2P")*100.0/col("2PA"))    //transforming the data
      .otherwise(col("2P%")))
      .withColumn("3P%", when(col("3P%").isNull, col("3P")*100.0/col("3PA")).otherwise(col("3P%")))
      .withColumn("FT%", when(col("FT%").isNull, col("FT")*100.0/col("FTA")).otherwise(col("FT%")))
      .withColumn("Awards", when(col("Awards").isNull, "none").otherwise(col("Awards")))
      .drop(col("eFG%"))
    val rename_columns = replaced_null.withColumnRenamed("Rk","rank")
      .withColumnRenamed("Player", "player_name")
      .withColumnRenamed("Age", "age")
      .withColumnRenamed("Team", "team")
      .withColumnRenamed("Pos", "position")
      .withColumnRenamed("G", "games")
      .withColumnRenamed("GS","games_started")
      .withColumnRenamed("MP","minutes_played")
      .withColumnRenamed("FG","field_goals_made")
      .withColumnRenamed("FGA","field_goals_attempts")
      .withColumnRenamed("FG%","field_goal_percentage")
      .withColumnRenamed("3P", "three_point_field_goals_made")
      .withColumnRenamed("3PA", "three_point_field_goals_attempts")
      .withColumnRenamed("3P%", "three_point_percentage")
      .withColumnRenamed("2P", "two_point_field_goals_made")
      .withColumnRenamed("2PA", "two_point_field_goal_attempts")
      .withColumnRenamed("2P%", "two_point_percentage")
      .withColumnRenamed("FT", "free_throw_made")
      .withColumnRenamed("FTA", "free_throw_attempts")
      .withColumnRenamed("FT%", "free_throw_percentage")
      .withColumnRenamed("ORB", "offensive_rebound")
      .withColumnRenamed("DRB","defensive_rebound")
      .withColumnRenamed("TRB","total_rebounds")
      .withColumnRenamed("AST","assists")
      .withColumnRenamed("STL","steals")
      .withColumnRenamed("BLK","blocks")
      .withColumnRenamed("TOV","turnovers")
      .withColumnRenamed("PF","personal_fouls")
      .withColumnRenamed("PTS","points")
    val Players = rename_columns.filter(col("player_name") =!= "player").drop(col("_c0"))

    //Players.show()
    val url1 = "jdbc:postgresql://localhost:5432/NBA"                                //writing to postgres
        val table1 = "Players"
        val props1 = new java.util.Properties()
        props1.setProperty("user", "postgres")
        props1.setProperty("password", "######")
        props1.setProperty("driver", "org.postgresql.Driver")

        Players.write.mode("append").jdbc(url1, table1, props1)




    //ratings table

    val schema_ratings = StructType(List(                                        //schema definition
      StructField("_c0", IntegerType),
      StructField("Rk", IntegerType),
      StructField("Team", StringType),
      StructField("Conf",StringType),
      StructField("Div", StringType),
      StructField("W", IntegerType),
      StructField("L", IntegerType),
      StructField("W/L%", FloatType),
      StructField("MOV", FloatType),
      StructField("ORtg", FloatType),
      StructField("DRtg", FloatType),
      StructField("NRtg", FloatType),
      StructField("MOV/A", FloatType),
      StructField("ORtg/A", FloatType),
      StructField("DRtg/A", FloatType),
      StructField("NRtg/A", FloatType),
      StructField("Year", IntegerType)
    ))


    val Second = NBA_.read                                                                    //reading the data
      .format("csv")
      .option("header", true)
      .schema(schema_ratings)
      .option("path", "C:/Users/LATEEF/Downloads/NBAproject/ratings.csv")
      .load()

    val ratings = Second.withColumnRenamed("Rk", "rank")                                  //transforming the data
      .withColumnRenamed("Team","team")
      .withColumnRenamed("Conf","conference")
      .withColumnRenamed("Conf","conference")
      .withColumnRenamed("Div", "division")
      .withColumnRenamed("W", "win")
      .withColumnRenamed("L", "loss")
      .withColumnRenamed("W/L%", "winning_percentage")
      .withColumnRenamed("MOV", "margin_of_victory")
      .withColumnRenamed("ORtg", "offensive_rating")
      .withColumnRenamed("DRtg", "defensive_rating")
      .withColumnRenamed("NRtg", "net_rating")
      .withColumnRenamed("MOV/A", "adjusted_margin_of_victory")
      .withColumnRenamed("ORtg/A", "adjusted_offensive_rating")
      .withColumnRenamed("DRtg/A", "adjusted_defensive_rating")
      .withColumnRenamed("NRtg/A", "adjusted_net_ratng")
      .withColumnRenamed("Year", "year").drop(col("_c0"))


    //ratings.show()


    val url2 = "jdbc:postgresql://localhost:5432/NBA"                                        //writing to postgres
    val table2 = "ratings"
    val props2 = new java.util.Properties()
        props2.setProperty("user", "postgres")
        props2.setProperty("password", "######")
        props2.setProperty("driver", "org.postgresql.Driver")

        ratings.write.mode("append").jdbc(url2, table2, props2)

    //conference

    val schema_conference = StructType(List(                                                         //schema definition
      StructField("_c0", IntegerType),
      StructField("W", IntegerType),
      StructField("L", IntegerType),
      StructField("W/L%", FloatType),
      StructField("GB", IntegerType),
      StructField("PS/G", FloatType),
      StructField("PA/G", FloatType),
      StructField("SRS", FloatType),
      StructField("Year", IntegerType),
      StructField("Team", StringType)))

    val Third = NBA_.read                                                                             //reading the data
      .format("csv")
      .option("header", true)
      .schema(schema_conference)
      .option("path", "C:/Users/LATEEF/Downloads/NBAproject/teams_conf_standings.csv")
      .load()

    val teams_conference_standings = Third.withColumnRenamed("W", "wins")                        //transforming the data
      .withColumnRenamed("L", "losses")
      .withColumnRenamed("W/L%", "winning_percentage")
      .withColumnRenamed("GB", "games_behind")
      .withColumnRenamed("PS/G", "points_scored_per_game")
      .withColumnRenamed("PA/G", "points_allowed_per_game")
      .withColumnRenamed("SRS", "simple_rating_system").drop(col("_c0"))
      .withColumnRenamed("Year", "year")
      .withColumnRenamed("Team", "team")



        val url3 = "jdbc:postgresql://localhost:5432/NBA"                                          //writing to postgres
        val table3 = "teams_conference_standings"
        val props3 = new java.util.Properties()
        props3.setProperty("user", "postgres")
        props3.setProperty("password", "######")
        props3.setProperty("driver", "org.postgresql.Driver")

        teams_conference_standings.write.mode("overwrite").jdbc(url3, table3, props3)


  }

}
