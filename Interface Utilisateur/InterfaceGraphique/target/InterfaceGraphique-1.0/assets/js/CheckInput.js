function validateInputs(){
    var disableButton = false;
    var val1 = document.getElementById("searchVal");
    if(val1.value.length == 0)
        disableButton = true;
    document.getElementById("searchButton").disabled= disableButton;
}