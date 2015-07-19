package com.umkc.sparkML

import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Venu on 7/18/15.
 */
object SparkMLRG {
  def main(args: Array[String]) {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkSVM").set("spark.driver.memory", "4g")
    val sc = new SparkContext(conf)

    val hashingTF = new HashingTF(numFeatures = 100)

    //val inputFile = sc.textFile("src/main/resources/imdb_labelled.txt").filter(line => !line.isEmpty).filter( r => r.trim.last.toInt == 1 ).saveAsTextFile("src/main/resources/lema")

   // val inputFile = sc.textFile("src/main/resources/imdb_labelled.txt").filter(line => !line.isEmpty).map( r => r.trim.last ).filter(q => q.trim.toInt == 1)



  }

}
