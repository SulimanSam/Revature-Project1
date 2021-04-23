let userinfo;
window.onload = function() {
    document.getElementById("email").focus();
    //document.getElementById("role").disabled =true;

    fetch(`http://localhost:9001/getUserInfo`)
    .then(
        function(response){
            const re = response.json();
            console.log(re);
            return re;
        }
    ).then(
        function(response2){
    
            console.log(response2);
            
            let user = document.getElementById("currentUser");
            user.innerText=response2.firstName+" "+response2.lastName;
            
            
            userinfo=response2;
        }
    ).catch(
        (e) => {
            console.log("Exception");
            console.log(e);
    }
    );
  }


  ///////////////////////////////////////////////////////////////////////////
  const newUserForm = document.getElementById("newUser-form");
  const userRole = document.getElementById("user-role");


  userRole.addEventListener('click', function() {
    if (this.value=="employee") {
               
        this.value="manager";
        this.blur();
        
    } else {
        
        this.value="employee";
        this.blur();
    }
  });



  newUserForm.addEventListener("submit",function(event) {
    event.preventDefault();

    ////////////////////////////////////////new request//////////////////////////////////////////////
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
    if(xhttp.readyState===4 && xhttp.status===200){            
        let re = xhttp.responseText;
        console.log(re);
        if (re>0){

          window.swal({ 
            title: 'Profile Created',
            type: 'success',
            timer: 4000,
            showConfirmButton: true
          }) 
        }else{
            window.swal({ 
                title: "This user is already exist",
                type: 'warning',
                showConfirmButton: true
              })
        }
    }
  }    
    xhttp.open("POST", `http://localhost:9001/newUser`);
    let firstName =document.getElementById("firstname").value;
    let lastName =document.getElementById("lastname").value;
    let email =document.getElementById("email").value;
    let userName =document.getElementById("username").value;
    let password =document.getElementById("password").value;
    let usrRole =document.getElementById("user-role").value;
    let userInfo;

    userInfo = {
        "userID":0,
        "userName":userName,
        "firstName":firstName,
        "lastName":lastName,
        "password":password,
        "email":email,
        "date":null,
        "isChange":1,
        "role":usrRole
        }
    
    xhttp.setRequestHeader("content-type", "application/json");

    xhttp.send(JSON.stringify(userInfo));

    window.swal({
        title: 'Creating',
        allowEscapeKey: false,
        allowOutsideClick: false,
        showConfirmButton: false,
        timer: 8000,
        onOpen: () => {
          swal.showLoading();
        }
        
      })
    
      
    

  });