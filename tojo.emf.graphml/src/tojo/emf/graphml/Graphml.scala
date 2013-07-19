package tojo.emf.graphml

import scala.collection.JavaConversions._
import scala.collection._
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList
import scala.util.Random

object Graphml {

    def nameFeature = "shortName"

    def name(eo: EObject) = for {
        feature <- Option(eo.eClass().getEStructuralFeature(nameFeature))
        value <- Option(eo.eGet(feature))
        name <- value match {
            case n: String => Some(n)
            case _         => None
        }
    } yield name
    
    def color(eo: EObject): String = {
        val rand = new Random(eo.eClass.getName.hashCode)
        val hexs = for (_ <- 1 to 6) yield (rand.nextInt(10) + 5).toHexString
        "#" + hexs.mkString
    }

    def include(eo: EObject): Boolean = eo.eClass.getName match {
        case "IdentifiableAllExtensionsMapEntry" => false
        case "AdminData"                         => false
        case "EStringToStringMapEntry"           => false
        case _                                   => true
    }

    def traverse(eo: EObject) = {

        val included = mutable.Set[EObject]()
        val objs = mutable.ListBuffer[String]()
        val clds = mutable.ListBuffer[String]()
        val refs = mutable.ListBuffer[String]()

        included += eo
        eo.eAllContents filter (include) foreach (included += _)

        add(eo)
        eo.eAllContents filter (include) foreach (add(_))

        def add(eo: EObject): Unit = {
            objs += obj(eo, name(eo) getOrElse (""), eo.eClass.getName)
            eo.eContents filter (included.contains(_)) foreach { cc =>
                clds += cld(eo, cc)
            }
            eo.eClass.getEReferences filter { r =>
                !r.isContainer && !r.isContainment
            } foreach { r =>
                eo.eGet(r) match {
                    case e: EObject if (included.contains(e)) => refs += ref(eo, e, r.getName)
                    case l: EList[_] => l foreach {
                        _ match {
                            case e: EObject if (included.contains(e)) => refs += ref(eo, e, r.getName)
                            case _                                    =>
                        }
                    }
                    case _ =>
                }
            }
        }

        import tojo.emf.graphml.GraphmlBuilder._

        def id(eo: EObject): String = eo.hashCode.toString

        def obj(eo: EObject, name: String, tpe: String) = node(id(eo), name + " : " + eo.eClass.getName, color(eo))

        def cld(p: EObject, c: EObject) = child(id(p) + id(c), id(p), id(c))

        def ref(s: EObject, t: EObject, name: String) = reference(id(s) + id(t), id(s), id(t))

        val wr = new java.io.FileWriter("""C:\Users\tojo\git\emf2graphml\tojo.emf.graphml\test.graphml""")
        wr.write(start)
        wr.write(objs.mkString("\n", "\n", "\n"))
        wr.write(clds.mkString("\n", "\n", "\n"))
        wr.write(refs.mkString("\n", "\n", "\n"))
        wr.write(end)

        wr.close()
    }

}