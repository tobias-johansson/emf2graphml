package tojo.emf.graphml

object GraphmlBuilder {

    def node(id: String, title: String): String =
        node(id, title, "", "50", "100", "0", "0", "#FFCC00")

    def node(id: String, title: String, color: String): String =
        node(id, title, "", "50", "100", "0", "0", color)

    def node(id: String, title: String, url: String, height: String, width: String, x: String, y: String, color: String): String = s"""
      <node id="$id">
      <data key="d4"><![CDATA[$url]]></data>      
      <data key="d6">
        <y:ShapeNode>
          <y:Geometry height="$height" width="$width" x="$x" y="$y"/>
          <y:Fill color="$color" transparent="false"/>
          <y:BorderStyle color="#000000" type="line" width="1.0"/>
          <y:NodeLabel alignment="center" autoSizePolicy="content" fontFamily="Dialog" fontSize="12" fontStyle="plain" hasBackgroundColor="false" hasLineColor="false" height="18.701171875" modelName="internal" modelPosition="c" textColor="#000000" visible="true" width="29.998046875" x="5.0009765625" y="5.6494140625">$title</y:NodeLabel>
          <y:Shape type="roundrectangle"/>
        </y:ShapeNode>
      </data>
    </node>"""

    def child(id: String, src: String, dst: String) =
        edge(id, src, dst, "diamond")

    def reference(id: String, src: String, dst: String) =
        edge(id, src, dst, "standard")

    def edge(id: String, src: String, dst: String, arrow: String) = s"""
      <edge id="$id" source="$src" target="$dst">
      <data key="d9"/>
      <data key="d10">
        <y:QuadCurveEdge straightness="0.1">
          <y:Path sx="0.0" sy="0.0" tx="0.0" ty="0.0"/>
          <y:LineStyle color="#000000" type="line" width="1.0"/>
          <y:Arrows source="none" target="$arrow"/>
        </y:QuadCurveEdge>
      </data>
    </edge>"""

    // <y:Arrows source="none" target="diamond"/>

    def start = """<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<graphml xmlns="http://graphml.graphdrawing.org/xmlns" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:y="http://www.yworks.com/xml/graphml" xmlns:yed="http://www.yworks.com/xml/yed/3" xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd">
  <!--Created by yFiles for Java 2.8-->
  <key for="graphml" id="d0" yfiles.type="resources"/>
  <key for="port" id="d1" yfiles.type="portgraphics"/>
  <key for="port" id="d2" yfiles.type="portgeometry"/>
  <key for="port" id="d3" yfiles.type="portuserdata"/>
  <key attr.name="url" attr.type="string" for="node" id="d4"/>
  <key attr.name="description" attr.type="string" for="node" id="d5"/>
  <key for="node" id="d6" yfiles.type="nodegraphics"/>
  <key attr.name="Description" attr.type="string" for="graph" id="d7"/>
  <key attr.name="url" attr.type="string" for="edge" id="d8"/>
  <key attr.name="description" attr.type="string" for="edge" id="d9"/>
  <key for="edge" id="d10" yfiles.type="edgegraphics"/>
  <graph edgedefault="directed" id="G">
    <data key="d7"/>
"""

    def end = """
  </graph>
  <data key="d0">
    <y:Resources/>
  </data>
</graphml>
"""

}