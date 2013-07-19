package tojo.emf.graphml

import scala.collection.JavaConversions._
import scala.collection._
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.EList

object Graphml {

    def traverse(eo: EObject) = {

        val included = mutable.Set[EObject]()
        val objs = mutable.ListBuffer[String]()
        val clds = mutable.ListBuffer[String]()
        val refs = mutable.ListBuffer[String]()

        included += eo
        eo.eAllContents foreach (included += _)

        add(eo)
        eo.eAllContents foreach (add(_))

        println(objs.mkString("\n"))
        println(clds.mkString("\n"))
        println(refs.mkString("\n"))

        def add(eo: EObject): Unit = {
            objs += obj(eo)
            eo.eContents filter (included.contains(_)) foreach { cc =>
                clds += cld(eo, cc)
            }
            eo.eClass.getEReferences filter { r =>
                !r.isContainer && !r.isContainment
            } foreach { r =>
                eo.eGet(r) match {
                    case e: EObject => refs += ref(eo, e, r.getName)
                    case l: EList[_] => l foreach {
                        _ match {
                            case e: EObject => refs += ref(eo, e, r.getName)
                            case _          =>
                        }
                    }
                    case _ =>
                }
            }
        }

        def id(eo: EObject): String = eo.hashCode.toString

        def obj(eo: EObject) = id(eo) + " : " + eo.eClass.getName

        def cld(p: EObject, c: EObject) = id(p) + "  / " + id(c)

        def ref(s: EObject, t: EObject, name: String) = id(s) + " -> " + id(t) + " " + name
    }

}