package com.umkc.sparkML

import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Venu on 7/17/15.
 */
object SparkBIG {

  def main(args: Array[String]) {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkSVM").set("spark.driver.memory", "4g")
    val sc = new SparkContext(conf)

    val hashingTF = new HashingTF(numFeatures = 100)

//    val positive = sc.textFile("src/main/resources/Positive").filter(r => !r.isEmpty)
//
//    val negative = sc.textFile("src/main/resources/Negative").filter(l => !l.isEmpty)
//
//    val positiveFeatures = positive.map(pt => hashingTF.transform(positive.split()))

    val positive = sc.textFile("src/main/resources/Positive")
    val negative = sc.textFile("src/main/resources/Negative")

    // Create a HashingTF instance to map email text to vectors of 100 features.

    // Each email is split into words, and each word is mapped to one feature.
    val positiveFeatures = positive.map(email => hashingTF.transform(email.split(" ")))
    val negativeFeatures = negative.map(email => hashingTF.transform(email.split(" ")))

    val positiveExamples = positiveFeatures.map(features => LabeledPoint(1, features))
    val negativeExamples = negativeFeatures.map(features => LabeledPoint(0, features))

    val trainedData = positiveExamples ++ negativeExamples

    trainedData.cache()


    val lrLearner = new LogisticRegressionWithSGD()

    val model = lrLearner.run(trainedData)

    val testing = sc.textFile("src/main/resources/Testing")
//    val negative = sc.textFile("src/main/resources/Negative")

    // Create a HashingTF instance to map email text to vectors of 100 features.

    // Each email is split into words, and each word is mapped to one feature.
    val testingFeatures = testing.map(email => hashingTF.transform(email.split(" ")))
    //val negativeFeatures = negative.map(email => hashingTF.transform(email.split(" ")))

    val p = model.predict(testingFeatures)

    model.save(sc, "src/main/resources/test/")

    println(s"Prediction : $p")


    p.collect().foreach(println)











    sc.stop()
  }

}
