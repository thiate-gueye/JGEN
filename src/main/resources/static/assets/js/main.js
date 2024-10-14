const links = document.querySelectorAll('a.pe-none');
const deconnexion = document.getElementById("dec");
const connexion = document.getElementById("con");

const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))

// Capitalisation bouton suivant
function nextForm(bouton) {
    let nom = bouton.getAttribute("name");
    switch (nom) {
        case 'bn1':
            document.getElementById('f2').style.display = 'block';
            document.getElementById('f1').style.display = 'none';
            break;
        case 'bn2':
            document.getElementById('f3').style.display = 'block';
            document.getElementById('f2').style.display = 'none';
            break;
        case 'bn3':
            document.getElementById('f4').style.display = 'block';
            document.getElementById('f3').style.display = 'none';
            break;
    }
}

// Capitalisation bouton precedent
function prevForm(bouton) {
    let nom = bouton.getAttribute("name");
    switch (nom) {
        case 'bp1':
            document.getElementById('f1').style.display = 'block';
            document.getElementById('f2').style.display = 'none';
            break;
        case 'pb2':
            document.getElementById('f2').style.display = 'block';
            document.getElementById('f3').style.display = 'none';
            break;
        case 'bp3':
            document.getElementById('f3').style.display = 'block';
            document.getElementById('f4').style.display = 'none';
            break;
    }
}

function showPanel(panelId) {
    // Masquer tous les panneaux
    let panels = document.getElementsByClassName('panel');
    for (let i = 0; i < panels.length; i++) {
        panels[i].style.display = 'none';
    }

    // Afficher le panneau correspondant à panelId
    let panelToShow = document.getElementById(panelId);
    if (panelToShow) {
        panelToShow.style.display = 'block';
    }
}

// Objectifs spécifiques ajout
function addInput() {
    let container = document.getElementById('inputContainer');
    let newInput = document.createElement('div');
    newInput.classList.add('form-floating', 'mb-3');
    newInput.innerHTML = `<input type="text" class="form-control" required name="objectifs[]" placeholder="Objectif spécifique">
                              <label>Objectif spécifique</label>`;
    container.appendChild(newInput);
}

// Objectifs specifiques suppression
function removeInput() {
    let container = document.getElementById('inputContainer');
    let inputs = container.getElementsByClassName('form-floating');
    if (inputs.length > 1) {
        container.removeChild(inputs[inputs.length - 1]);
    } else {
        alert("Vous ne pouvez pas supprimer le dernier champ.");
    }
}

// Ajout contrainte capitalisation

function newInput(containerId, inputName, placeholder) {
    let container = document.getElementById(containerId);
    let newInput = document.createElement('div');
    newInput.classList.add('form-floating', 'mb-3');
    newInput.innerHTML = `<input type="text" class="form-control" required name="${inputName}[]" placeholder="${placeholder}">
                              <label>${placeholder}</label>`;
    container.appendChild(newInput);
}

function remokeInput(containerId) {
    let container = document.getElementById(containerId);
    let inputs = container.getElementsByClassName('form-floating');
    if (inputs.length > 1) {
        container.removeChild(inputs[inputs.length - 1]);
    } else {
        alert("Vous ne pouvez pas supprimer le dernier champ");
    }
}

deconnexion.addEventListener('click', () =>{
    links.forEach((link) => {
        link.classList.add("disabled");
    });
    sessionStorage.clear();
    console.log("connexion :"+ sessionStorage.getItem("con"));
});

connexion.addEventListener('click', () =>{
    links.forEach(function(link) {
        link.classList.remove("pe-none");
    });
});

