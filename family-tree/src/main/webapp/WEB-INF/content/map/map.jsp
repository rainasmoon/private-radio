<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<STYLE TYPE="text/css">
<!--
.map {
	position: absolute;
}

.node {
	position: absolute;
	border: 1px solid #658A16;
	height: 20px;
	width: 50px;
}

.line {
	position: absolute;
	height: 1px;
}

.vline {
	position: absolute;
	width: 1px;
	height: 50px;
}
-->
</STYLE>
<head>
	<title>family-tree</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/js/utils.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	var NODE_HEIGHT = 20;
    var NODE_WIDETH = 50;
    var RELATION_LENGTH = 100;
    var VLINE = 50;
    
	function Person(id, x, y, name) {
	    var o = new Object();
	    o.id = id;
		o.x = x;
		o.y = y;
		o.name = name;
		return o;
	}
	
	function Marriage(husband, wife) {
	    var m = new Object();
	    m.husband = husband;
	    m.wife = wife;	    
	    return m;
	}
	
	function Family(marriage) {
		var f = new Object();
		f.marriage = marriage;
		f.children = new Array();
		return f;
	}
	
	function addNode(id, x, y, s) {
		return "<div id=\"node_" + id + "\" class=\"node\" style=\"left:" + x + "px; top:" + y + "px;\" onclick=\"showMenu(" + id + ")\">" + s + "</div>";
	}
	function addLine(x, y, l) {
		return "<hr class=\"line\" style=\"left:" + x + "px; top:" + y + "px; width:" + l + "px;\"/>"
	}

	function addVline(x, y) {
		return "<hr class=\"vline\" style=\"left:" + x + "px; top:" + y + "px;\"/>";
	}
		
	function addPerson(person) {
		return addNode(person.id, person.x, person.y, person.name);
	}
	
	
	function addWife(husband, wife) {
		var html = "";
		html += addLine(husband.x + NODE_WIDETH, husband.y + NODE_HEIGHT/2, RELATION_LENGTH);
		
		wife.x = husband.x + NODE_WIDETH + RELATION_LENGTH;
		wife.y = husband.y;
		
		html += addPerson(wife);	 
		   
	    return html;
	}
	
	function addChild(family, child) {
		var html = "";
		html += addVline(family.marriage.husband.x + NODE_WIDETH + RELATION_LENGTH/2, family.marriage.husband.y + NODE_HEIGHT/2);
						
		child.x = family.marriage.husband.x + NODE_WIDETH/2 +RELATION_LENGTH/2 + family.children.length * (RELATION_LENGTH + NODE_WIDETH)/2;
		child.y = family.marriage.husband.y + NODE_HEIGHT/2 + VLINE*2;
		
		if (family.children.length == 0) {			
			html += addVline(child.x + NODE_WIDETH/2 , child.y - VLINE);
		}
		else {
			html += addLine(child.x - RELATION_LENGTH - NODE_WIDETH/2 , child.y - VLINE, RELATION_LENGTH + NODE_WIDETH);
			html += addVline(child.x + NODE_WIDETH/2 , child.y - VLINE);
		}
			
				
		family.children[family.children.length + 1] = child;
		
	    html += addPerson(child);
	    return html;
	}
	
	function drawGraph() {
	    var html = "";
	    var x = 100;
	    var y = 20;
	    var grandpa = Person(1, x, y, "grandpa");
	    
	    html += addPerson(grandpa);
	    
	    var grandma = Person(2, null, null, "grandma");	    
	    html += addWife(grandpa, grandma);
	    var grandMarriage = Marriage(grandpa, grandma);
	    var grandFamily = Family(grandMarriage); 
	    var father = Person(3, null, null, "father");		       
	    html += addChild(grandFamily, father);	    
	    
	    var mother = Person(4, null, null, "mother");
	    html += addWife(father, mother);
	    var marriage = Marriage(father, mother);
	    var family = Family(marriage);
	   
	    var child1 = Person(5, null, null, "child1");
	    html += addChild(family, child1);
	    
	    var child2 = Person(6, null, null, "child2");
	    html += addChild(family, child2);
	    
	    var child3 = Person(7, null, null, "child3");
	    html += addChild(family, child3);
	    	    
	    document.getElementById("map").innerHTML = html;
	}
		
	function showMenu(id) {
			
		var html = "";
				
		html += "<ul>";
		html += "<li><a href=\"javascript:openMarriageNode(" + id + ")\">open marriage</a></li>";
		html += "<li><a href=\"javascript:openFamilyNode(" + id + ")\">open family</a></li>";
		html += "<li><a href=\"javascript:closeNode(" + id + ")\">close</a></li>";
		html += "<li><a href=\"javascript:createPartner(" + id + ")\">create partner</a></li>";
		html += "<li><a href=\"javascript:createChild(" + id + ")\">create child</a></li>";
		html += "</ul>";
		
		var o = document.createElement("div"); 
		o.style.position="absolute";
	    o.style.top=document.getElementById("node_" + id).style.top;	
	    o.style.left=document.getElementById("node_" + id).style.left; 
	    
		o.innerHTML=html; 
		document.getElementById("map").appendChild(o); 
	}
		
	function openMarriageNode(id) {
	    alert("marriage" + id);
		return;
	}
	
	function openFamilyNode(id) {
		alert("family");
	}
	
	function closeNode(id) {
		alert("close");
	}
	
	function createPartner(id) {
		
		alert("createPartner");
	}
	
	function createChild(id) {
		alert("createChild");
	}
	
	$(document).ready(function() {			
		$("#name").focus();	
	});
	
	$(document).keypress(function(event) {
		  if ( event.which == 13 ) {
		     event.preventDefault();
		     search();
		   } 
	});
	
	function search() {
		$("#mainForm").submit();
	}
	
	function saveFamily() {		
		$.get("map!saveFamily.action?peopleId=${people.id}&" + $("form").serialize(), function(data) {
			$('#message').text(data).show().fadeOut(2000);
			window.location.href = "map!map.action?peopleId=${people.id}";
		});
	}

</script>
</head>

<body>
<div id="doc3">
<%@ include file="/common/header.jsp" %>
</div>
<div id="message" style="display:none;"></div>
<input type="button" value="show the magic" onclick="drawGraph();" />

<div id="map" class="map">
</div>
<div>
who's family? :${ people.name }
</div>
<div>
    <form id="familyForm">
	<div>Grandpa:<a href="map!map.action?peopleId=${ grandPa.id }">${ grandPa.name }</a></div>
	<div>Grandma:<a href="map!map.action?peopleId=${ grandMa.id }">${ grandMa.name }</a></div>
	<div>Father:
	<s:if test="father.id != null">
	<a href="map!map.action?peopleId=${ father.id }">${ father.name }</a>
	</s:if>
	<s:else><input type="text" fatherName"></input></s:else>
	</div>
	<div>Mother:
	<s:if test="father.id != null">
	<a href="map!map.action?peopleId=${ mother.id }">${ mother.name }</a>
	</s:if>
	<s:else>
	<input type="text" name="motherName"></input>
	<input type="button" value="save" onclick="saveFamily();"/>
	</s:else>
	</div>
	<div>Sibling:
		<ul>
			<s:iterator value="siblingList">		
				<li><a href="map!map.action?peopleId=${ id }">${name}</a>&nbsp;</li>		
			</s:iterator>
		</ul>
	</div>
	</form>
	<div>
	marriage:
	     <ul>
			<s:iterator value="marriageList">		
				<li><a href="map!map.action?peopleId=${ husband.id }">${husband.name}</a>+<a href="map!map.action?peopleId=${ wife.id }">${ wife.name }</a>&nbsp;</li>
				<li>
					<ul>
						<s:iterator value="childrenList">		
							<li><a href="map!map.action?peopleId=${ people.id }">${people.name}</a>&nbsp;</li>							
						</s:iterator>
					</ul>
				</li>
			</s:iterator>
		</ul>
	</div>
		
</div>

<div>
	<form id="mainForm" action="map.action" method="get">
	    <div id="error">
	        <s:actionerror theme="custom" cssClass="fail" />
	    </div>
		<div id="message">
			<s:actionmessage theme="custom" cssClass="success" />			
		</div>
		<div id="filter">
			name: <input type="text" id="filter_LIKES_name" name="filter_LIKES_name"
				value="${param['filter_LIKES_name']}"/> <input
				type="button" value="search" onclick="search();" />
		</div>		
	</form>
</div>

<div id="content">
		<s:iterator value="peopleList">
		<ul>
			<li>${name}&nbsp;</li>
			<li>${sex}&nbsp;</li>
			<li><a href="map!map.action?peopleId=${ id }">view relations</a></li>
		</ul>
	</s:iterator>
</div>

<div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
