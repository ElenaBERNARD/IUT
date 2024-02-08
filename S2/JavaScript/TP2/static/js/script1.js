function getClientDetails(codeClient){
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange= function(){
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            var response = JSON.parse(xmlhttp.responseText) [0];
            document.getElementById('details').innerHTML=response.civilite+" "+response.nom+" "+response.prenom;
        }
    }

    xmlhttp.open("GET", "/api/client?codeClient="+codeClient, true);

    xmlhttp.send();
}

var nbLigne = 1

function getProduct(){
    var xmlhttp = new XMLHttpRequest();
 
    xmlhttp.onreadystatechange= function(){
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            var response = JSON.parse(xmlhttp.responseText);
            console.log(response);
            for(i = 0; i < nbLigne; i++){
                var liste = document.getElementById("designationSelect"+i);
                var option = document.createElement("option");
                option.text="vide";
                option.value="-1";
                liste.appendChild(option);

                for(j=0; j<response.length; j++){
                    var option = document.createElement("option");
                    option.text=response[j].prestation;
                    option.value=response[j].codePrestation;
                    liste.appendChild(option);
                }
            }
        }
    }
    xmlhttp.open("GET", "/api/products", true);

    xmlhttp.send();
}

function getProductDetails(codeProduit, n){
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange= function(){
        if(xmlhttp.readyState==4 && xmlhttp.status==200){
            var response = JSON.parse(xmlhttp.responseText)[0];
            console.log(response);
            document.getElementById('c'+ (n+2) +'3').innerHTML=response.prix;
        }
    }

    xmlhttp.open("GET", "/api/product?codePrestation="+codeProduit, true);

    xmlhttp.send();
}