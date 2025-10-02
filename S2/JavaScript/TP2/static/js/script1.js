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
            for(i = 0; i < nbLigne; i++){
                let liste = document.getElementById("designationSelect"+i);
                if(liste.innerHTML.length == 0){
                    let option = document.createElement("option");
                    option.text="vide";
                    option.value="-1";
                    liste.appendChild(option);

                    for(j=0; j<response.length; j++){
                        let option = document.createElement("option");
                        option.text=response[j].prestation;
                        option.value=response[j].codePrestation;
                        liste.appendChild(option);
                    }
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
            document.getElementById('c'+ (n+2) +'1').innerHTML=response.codePrestation;
            document.getElementById('c'+ (n+2) +'3').innerHTML=response.prix;
            calculateTotal()
        }
    }

    xmlhttp.open("GET", "/api/product?codePrestation="+codeProduit, true);

    xmlhttp.send();
}

function getProductQuantity(quantite, n){
    var prix = document.getElementById('c'+ (n+2) +'3').innerHTML;
    document.getElementById('c'+ (n+2) +'5').innerHTML=prix*quantite;
    calculateTotal()
}

function addLine(){
    let colone = "c" + (this.nbLigne+2);
    let element = "<td id=\"" + colone + "1\"></td><td id=\""+ colone + "2\"> <select onchange=\"getProductDetails(this.value, " + nbLigne + ")\" id=\"designationSelect"+ nbLigne + "\"></select> </td> <td id=\"" + colone + "3\"}></td> <td id=\"" + colone + "4\"><input onchange=\"getProductQuantity(this.value, "+ nbLigne + ")\" type=\"text\"></input></td> <td id=\""+ colone + "5\"></td>"
    let newLine = document.createElement("tr");
    newLine.innerHTML = element;
    let endTable = document.getElementById("endTable");
    let table = endTable.parentNode;
    table.insertBefore(newLine, endTable);
    nbLigne++;
    getProduct();
}

function calculateTotal(){
    let sum = 0;
    for(i = 2; i < nbLigne + 2; i++){
        let n = Number.parseFloat(document.getElementById('c'+ i +'5').innerHTML);
        if(!isNaN(n)){
            sum += n;
        }
    }
    document.getElementById("end15").innerHTML = sum + "€";
    let reduction = 1 - Number.parseFloat(document.getElementById("end25").innerHTML)/100;
    let prix = Math.round(sum * reduction*100)/100;
    document.getElementById("end35").innerHTML = prix + "€";
}