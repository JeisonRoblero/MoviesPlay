//Cambio de header
function headerChange(){
  const header = document.querySelector('header')
  const nav = document.querySelector('header nav')

  if(scrollY > 300){
    header.style.background = 'rgba(0, 0, 0, 0.63)';
    header.style.setProperty('backdrop-filter','blur(20px)'); 
    nav.style.height = '56px';
  }else{
    header.style.background = 'transparent'
    header.style.setProperty('backdrop-filter','blur(0)'); 
    nav.style.height = '67px';
  }
}

window.addEventListener('scroll', headerChange);


//Header movil
const burger = document.querySelector('.burger');
const menu = document.querySelector('.menu');

burger.addEventListener('click', () => {
  menu.classList.toggle('mostrar');

  //Animación de Burger
  burger.classList.toggle('toggle');
});

//Buscador
const btnSearch = document.querySelector('.search-btn1');
const searchInput = document.querySelector('.buscador-container');
const btnBackSearch = document.querySelector('.back-search');
const inputSearch = document.querySelector('#search');
const btnCleanInput = document.querySelector('.clean-input');
const box_search = document.getElementById('box-search');
const noEncontrado = document.querySelector('no-encontrado');

function showSearch(){
  btnSearch.classList.toggle('toggle-search-btn1');
  searchInput.classList.toggle('toggle-search');
  box_search.style.display = "none";
}

btnSearch.addEventListener('click', showSearch );
btnBackSearch.addEventListener('click', showSearch );

var touchDevice = ('ontouchstart' in document.documentElement);

if (touchDevice==false){
  btnSearch.addEventListener('mouseenter', showSearch );
}

//Limpiando el input del buscador
function cleanInput(){

  if(inputSearch.value === ""){
    box_search.style.display = "none";
    showSearch();
  }else{
    box_search.style.display = "none";
    inputSearch.value = "";
  }
}

btnCleanInput.addEventListener('click', cleanInput );

//Busqueda en tiempo real
inputSearch.addEventListener("keyup", buscador_interno);

function buscador_interno(){

  filter = inputSearch.value.toUpperCase();
  li = box_search.getElementsByTagName("li");

  //Recorriendo elementos a filtrar mediante los "li"
  for (i = 0; i < li.length; i++){

    a = li[i].getElementsByTagName("a")[0];
    textValue = a.textContent || a.innerText;

    if(textValue.toUpperCase().indexOf(filter) > -1){

      li[i].style.display = "";
      box_search.style.display = "block";

      if (inputSearch.value === ""){
        box_search.style.display = "none";
      }

    }else{
      li[i].style.display = "none";
    }

  }

}

// Swipper de principal
var swiper = new Swiper(".mySwiper", {
    spaceBetween: 30,
    centeredSlides: true,
    autoplay: {
      delay: 10500,
      disableOnInteraction: true,
    },
    pagination: {
      el: ".swiper-pagination",
      clickable: true,
    },
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
});

// Swiper de tarjetas
document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.carousel');
    var instances = M.Carousel.init(elems);
});

// Swiper de populares
document.addEventListener('DOMContentLoaded', function() {
  var elems2 = document.querySelectorAll('.carousel2');
  var instances2 = M.Carousel.init(elems2, {
    dist: 0,
    padding: 40,
    shift: 20,
    duration: 200,
  });
});

//Modal agregar peliculas
document.addEventListener('DOMContentLoaded', function() {
  var elems = document.querySelectorAll('.modal');
  var instances = M.Modal.init(elems);
});

//Ejecutar Modal con Ajax jQuery
var script = document.createElement('script');
script.src = 'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js';
script.type = 'text/javascript';
document.getElementsByTagName('head')[0].appendChild(script);

//Llama a la función de tipo DELETE
function eliminarDeCircular(idCircular){
  $.ajax("CircularServlet?idcircular="+idCircular, {type:"DELETE", success: function (){
      window.location.reload(false);
  }});
}

function eliminarDeLde(idLde){
  $.ajax("DoblementeEnlazadaServlet?idlde="+idLde, {type:"DELETE", success: function (){
      window.location.reload(false);
  }});
}

function eliminarActor(nombreActor,idPelicula){
  $.ajax("ABBServlet?nombreActor="+nombreActor+"&idPelicula="+idPelicula, {type:"DELETE", success: function (){
      window.location.reload(false);
  }});
}





