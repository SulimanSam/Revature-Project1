const body = document.querySelector("body");
const loginbox = document.querySelector(".login-box");
const loginboxButton = document.querySelector(".login-box-button");
const closeButton = document.querySelector(".close-button");
const loginType = document.getElementById("login-type");
const rememberMe = document.getElementById("remember-me");
const loginForm = document.getElementById("login-form");
 

window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    // Handle page restore.
    window.location.reload();
  }
});
function handleForm(event) { 
  event.preventDefault(); 
  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function(){
    if(xhttp.readyState===4 && xhttp.status===200){            
        let re = xhttp.responseText;
        console.log(re);
        if (loginType.checked &&(re==1) ){

          window.swal({ 
            title: 'logged in as a Manger!',
            type: 'success',
            timer: 2000,
            showConfirmButton: false
          })

          window.location.href = "http://localhost:9001/ManagerHome.html";
        }else if(!loginType.checked && ( re==1 | re==3 )){
          window.swal({ 
            title: 'logged in as Employee!',
            type: 'success',
            timer: 2000,
            showConfirmButton: false
          })
          window.location.href = "http://localhost:9001/home.html";
        }else if(re==0){
          window.swal({ 
            title: 'Wrong Username/Email or password',
            type: 'warning',
            showConfirmButton: true
          })
        }else if(re==2){
          window.swal({ 
            title: "You can't login as a manager",
            type: 'warning',
            showConfirmButton: true
          })
        }
    }
  }    
    xhttp.open("post", `http://localhost:9001/login`);
    let email = document.getElementById("email").value;
    let pass = document.getElementById("password").value;
    let type = loginType.checked? "manager": "employee";
    let remember = rememberMe.checked? "true": "false";
    let userAuth = {
      "email":email,
      "password":pass,
      "loginType":type,
      "rememberMe":remember
    }
    xhttp.setRequestHeader("content-type", "application/json");
    console.log(userAuth);
    xhttp.send(JSON.stringify(userAuth));

    window.swal({
        title: 'Login....',
        allowEscapeKey: false,
        allowOutsideClick: false,
        showConfirmButton: false,
        timer: 8000,
        onOpen: () => {
          swal.showLoading();
        }
        
      });
    
    

} 
loginForm.addEventListener('submit', handleForm);

const openloginbox = () => {
  loginbox.classList.add("is-open");
  body.style.overflow = "hidden";
};

const closeloginbox = () => {
  loginbox.classList.remove("is-open");
  body.style.overflow = "initial";
};

document.onkeydown = evt => {
  evt = evt || window.event;
  if(evt.keyCode ===27)
  closeloginbox()
};

loginType.addEventListener('change', function() {
    if (this.checked) {
        document.getElementById("type-lable").innerText = "Manager Login";
    } else {
        document.getElementById("type-lable").innerText = "Employee Login";
    }
  });

  rememberMe.addEventListener('change', function() {
    if (this.checked) {
        document.getElementById("remember-lable").innerText = "Remember Me";
    } else {
        document.getElementById("remember-lable").innerText = "Don't Remember Me";
    }
  });

loginboxButton.addEventListener("click", openloginbox);
closeButton.addEventListener("click", closeloginbox);

