function effacer() {
    document.getElementById("taille").value = ""
    document.getElementById("masse").value = ""
}

function imprimer() {
    window.print()
}

function calculer() {
    let taille = Number.parseFloat(document.getElementById("taille").value)
    if(taille > 5){
        taille = taille / 100
    }
    let masse = Number.parseFloat(document.getElementById("masse").value)
    let imc = masse / (taille * taille)
    if (isNaN(masse) || isNaN(taille) || masse < 0 || taille < 0) {
        alert("Saisie invalide. Veuillez entrer des nombres positifs")
    }
    else {
        document.getElementById("resultat").innerHTML = imc
        if(document.getElementById("masc").checked){
            if (imc < 20.7) {
                document.getElementById("etat").innerHTML = "maigreur"
            }
            if (imc >= 20.7 && imc < 26.4) {
                document.getElementById("etat").innerHTML = "normal chez un adulte"
            }
            if (imc >= 26.4 && imc < 27.8) {
                document.getElementById("etat").innerHTML = "a la limite du surpoids"
            }
            if (imc >= 27.8 && imc < 31.1) {
                document.getElementById("etat").innerHTML = "surpoids"
            }
            if (imc >= 31.1) {
                document.getElementById("etat").innerHTML = "obesite"
            }
        }
        else{
            if (imc < 19.1) {
                document.getElementById("etat").innerHTML = "maigreur"
            }
            if (imc >= 19.1 && imc < 25.8) {
                document.getElementById("etat").innerHTML = "normal chez un adulte"
            }
            if (imc >= 25.8 && imc < 27.3) {
                document.getElementById("etat").innerHTML = "a la limite du surpoids"
            }
            if (imc >= 27.3 && imc < 32.3) {
                document.getElementById("etat").innerHTML = "surpoids"
            }
            if (imc >= 32.3) {
                document.getElementById("etat").innerHTML = "obesite"
            }
        }
    }
}

function show_message() {
    document.getElementById("message").style.display = "block"
}

function hide_message() {
    document.getElementById("message").style.display = "none"
}

function testTaille() {
    let taille = Number.parseFloat(document.getElementById("taille").value)
    if(isNaN(taille) || taille < 0){
        document.getElementById("labelTaille").style.color = "red"
        document.getElementById("errorTaille").style.display = "block"
    }
    else {
        document.getElementById("labelTaille").style.color = "black"
    }
}

function testMasse() {
    let masse = Number.parseFloat(document.getElementById("masse").value)
    if(isNaN(masse) || masse < 0){
        document.getElementById("labelMasse").style.color = "red"
        document.getElementById("errorMasse").style.display = "block"
    }
    else {
        document.getElementById("labelMasse").style.color = "black"
    }
}

var isCtrl = false;
document.onkeyup = function (e) {
    if (e.key === 'Control')
        isCtrl = false;
}
document.onkeydown = function (e) {
    if (e.key === 'Control') isCtrl = true;
    if (e.key === 'c' && isCtrl == true) {
        calculer();
    }
}