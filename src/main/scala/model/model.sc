import java.io._

import model.Data

val data = new Data("1")
val fileName = "data1"
val out = new ObjectOutputStream(new FileOutputStream(fileName))
out.writeObject(data)
out.close()
val in = new ObjectInputStream(new FileInputStream(fileName))
val o = in.readObject
in.close()
o.getClass
o.asInstanceOf[Data].value
