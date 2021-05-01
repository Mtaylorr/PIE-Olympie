<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <title>ROBOT ACCUEIL ENSTA</title>
    <link href="assets/css/style.css" rel="stylesheet" type="text/css" />
    <script>
        var posx=-1;
        var posy=-1;
    </script>
    <script src="assets/js/ShowHidePanel.js"></script>

</head>
<body>
<div class="body"></div>
<div class="grad"></div>
<div class="table-title" onclick="HomePage();">
    <h3>ROBOT D'ACCUEIL</h3>
    <span>ENSTA</span>
</div>
<img class="homeicon" src="/assets/images/home.jpg" alt="test">
<div id="table">
    <table class="table-fill">
        <thead>
            <tr>
                <th class="text-center">Search Result</th>
            </tr>
        </thead>
        <tbody class="table-hover">
            <c:if test="${salleExists == true}">
                <c:forEach items="${salles}" var="s">
                    <tr value="salle ${s.id}, ${s.nom}, etage ${s.etage}" onclick="ShowHide(this.getAttribute('value'),${s.x},${s.y});">
                        <td>"${s.id}", "${s.nom}", etage "${s.etage}" </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${personnelExists == true}">
                <c:forEach items="${personnels}" var="p">
                    <tr value="${p.nom} ${p.prenom}, bureau ${p.salle}" onclick="ShowHide(this.getAttribute('value'),${p.x},${p.y});">
                        <td>"${p.nom}", "${p.prenom}" , "${p.salle}"</td>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>
</div>
<div class="searchResult" id="panel">
    <input type="panel" value="0001 bureau rdc" name="searchResult" id="searchResultPanel" enabled/>
</div>
<div class="searchResult" id="buttons">
    <input type="submit" value="Guide me" name="searchButton" id="searchResultRightButton" class="searchButton" onclick="redirect(posx,posy);"/>
    <input type="button" value="Return" name="returnButton" id="searchResultLeftButton" onclick="ShowHide();" class="returnButton"/>
</div>
</body>
</html>