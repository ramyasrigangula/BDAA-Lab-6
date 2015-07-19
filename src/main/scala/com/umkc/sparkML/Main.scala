package com.umkc.sparkML

import scala.io.Source

import java.io.{FileWriter, BufferedWriter, File}

/**
 * Created by Venu on 7/18/15.
 */
object Main {

  def main(args: Array[String]) {


    //val filename  = "src/main/resources/SMSSpamCollection"

    val filename  = "src/main/resources/SMSSpamCollection"
    var i =1

    var j =1

    val file = new File("src/main/resources/spam" )

    val bw = new BufferedWriter(new FileWriter(file))


    val file1 = new File("src/main/resources/ham" )

    val bw1 = new BufferedWriter(new FileWriter(file1))



    for(line <- Source.fromFile(filename).getLines()){

      val value = line.trim.substring(0,line.indexOf("\t"))

      if(value.equalsIgnoreCase("spam")){

        bw.write(1+" " +line.replace("spam\t",""))
        bw.newLine()

        //i= i+1

      }
      else if(value.equalsIgnoreCase("ham")){

//        val file = new File("src/main/resources/ham"+j)
//        val bw = new BufferedWriter(new FileWriter(file))
        bw1.write(0+" "+ line.replace("ham\t",""))
        bw1.newLine()
       // bw.close()
        //j=j+1
      }
    }

    bw.close()
    bw1.close()
  }

}
