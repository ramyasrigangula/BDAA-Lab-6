package com.umkc.sparkML

import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Venu on 7/19/15.
 */
object SparkSpam {

  def main(args: Array[String]) {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkSVM").set("spark.driver.memory", "4g")
    val sc = new SparkContext(conf)

    val hashingTF = new HashingTF(numFeatures = 100)

    val spam = sc.textFile("src/main/resources/spam")
    val ham = sc.textFile("src/main/resources/ham")

    val spamFeatures = spam.map(email => hashingTF.transform(email.split(" ")))
    val hamFeatures = ham.map(email => hashingTF.transform(email.split(" ")))

    val spamExamples = spamFeatures.map(features => LabeledPoint(1, features))
    val hamExamples = hamFeatures.map(features => LabeledPoint(0, features))

    val trainedData = spamExamples ++ hamExamples

    trainedData.cache()


    val lrLearner = new LogisticRegressionWithSGD()

    val model = lrLearner.run(trainedData)

    val testing = sc.textFile("src/main/resources/Ramya")
    //    val negative = sc.textFile("src/main/resources/Negative")

    // Create a HashingTF instance to map email text to vectors of 100 features.

    // Each email is split into words, and each word is mapped to one feature.
    val testingFeatures = testing.map(email => hashingTF.transform(email.split(" ")))
    //val negativeFeatures = negative.map(email => hashingTF.transform(email.split(" ")))

    val p = model.predict(testingFeatures)

    model.save(sc, "src/main/resources/test1/")

    println(s"Prediction : $p")


    p.collect().foreach(println)



  }
}
