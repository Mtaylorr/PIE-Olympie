function ShowHide(val,x,y){

    var panel = document.getElementById("panel");
    var buttons = document.getElementById("buttons");
    var table = document.getElementById("table");
    var title = document.getElementById("searchResultPanel");

    if(panel.style.display=="block"){
        panel.style['pointer-events'] = "none";
        buttons.style['pointer-events'] = "none";
        panel.style.display = "none";
        buttons.style.display = "none";
        table.style.display = "block";
    }else{
        title.value = val;
        table.style.display = "none";
        panel.style.display = "block";
        panel.style['pointer-events']= "auto";
        buttons.style.display = "block";
        buttons.style['pointer-events'] = "auto";
        posx=x;
        posy=y;
    }
}

function HomePage(){
    window.location.replace("index.html");
}

function redirect(x,y){
    if(x!=-1 && y!=-1){
        var jspcall = "followme?x="+x+"&y="+y;
        window.location.href = jspcall;
    }
}