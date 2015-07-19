package com.umkc.sparkML

import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.{SparkContext, SparkConf}

import org.apache.spark.mllib.util.MLUtils

/**
 * Created by Venu on 7/16/15.
 */
object SparkMLBase {

  def main(args: Array[String]) {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkSVM").set("spark.driver.memory", "4g")
    val sc = new SparkContext(conf)



//    val inputFile = sc.textFile("src/main/resources/Testing").filter(line => !line.isEmpty).map(r => "r.substring(r.length-1)" + r.replaceAll() )
//
//    val inputText = sc.textFile("src/main/resources/Testing").filter(line => !line.isEmpty).map(r => r.replaceAll("[~!@#$%^&*()__.,]",""))
//
//    val inputTextCollect = inputText.map(r =>r.split(" ")).map(line => inputFile.collect().foreach(""))

    //val inputFile = sc.textFile("src/main/resources/Testing").filter(l => !l.isEmpty).map(r => r.trim.last +" "+ r.trim.init.replaceAll("*","11")).saveAsTextFile("src/main/resources/output")

    val hashTF = new HashingTF()

    val documents = sc.textFile("src/main/resources/Testing").filter(l=> !l.isEmpty).map(r =>r.trim.init.split(" ").toSeq)

    val hasTF =hashTF.transform(documents)



    println("------------------")

    val idf = new IDF().fit(hasTF)

    val tfidf = idf.transform(hasTF)




    hasTF.collect().foreach(println)
    hasTF.cache()


    tfidf.collect().foreach(println)



    //inputFile.collect().foreach(println)

//    inputText.collect().foreach(println)

    sc.stop()
  }

}
