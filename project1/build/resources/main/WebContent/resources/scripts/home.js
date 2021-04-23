
let Reimb ;
window.onload = function() {

    //Get User Information
  fetch(`http://localhost:9001/getUserInfo`)
  .then(
      function(response){
          const re = response.json();
          console.log(re);
          return re;
      }
  ).then(
      function(response2){
          
          let user = document.getElementById("currentUser");
          user.innerText=response2.firstName+" "+response2.lastName;
          if(response2.isChange==1){
            window.swal({
                title: 'Please Update Your Password',
                type: 'warning',
                allowEscapeKey: false,
                allowOutsideClick: false,
                showConfirmButton: true,
                timer: 8000,
                
              })
          }
      }
  ).catch(
      (e) => {
          console.log("exploded")
          
          console.log(e)
  }
  );

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){

       if(xhttp.readyState===4 && xhttp.status===200){

        let info = xhttp.responseText;
        let headers = xhttp.getAllResponseHeaders().toLowerCase();
        
        let jsonResponse = JSON.parse(xhttp.responseText);
            console.log(jsonResponse);
            Reimb = jsonResponse.json;
            addReimbursementinfo(jsonResponse.info);
            allTable(jsonResponse.json);
       }
   }

   xhttp.open("GET", `http://localhost:9001/getReimbursements`);
    
  
    xhttp.send();
}

function addReimbursementinfo(info){
    document.getElementById("ApprovedCard").innerText= info[0];
    document.getElementById("DeniedCard").innerText= info[1];
    document.getElementById("ApprovedAmount").innerText= "$ "+info[2];
    document.getElementById("Pending").innerText= info[3];
}
////////////////////
function allTable(json){
    let cont= document.getElementById("container");
    cont.innerHTML="";
    let table= document.createElement("table");
    let thead= document.createElement("thead");
    let tbody= document.createElement("tbody");
    table.setAttribute("class", "table table-striped");
    thead.setAttribute("class","text-primary");
    
    let th= document.createElement("tr");
    for(var i =1;i<11;i++){
       
        let td =document.createElement("td");
        switch(i){
        case 1: td.innerText= "ID";
        
        break;
        case 2: td.innerText= "Amount";
        
        break;
        case 3: td.innerText= "Submittion Date";
        
        break;
        case 4: td.innerText= "Resolve Date";
        
        break;
        case 5: td.innerText= "Description";
        
        break;
        case 10: td.innerHTML= '<i class="material-icons">insert_photo</i>';
        
        break;
        case 6: td.innerText= "Author";
        
        break;
        case 7: td.innerText= "Resolver";
        
        break;
        case 8: td.innerText= "Status";
        
        break;
        case 9: td.innerText= "Type";
        
        break;
        }
        th.appendChild(td);
    }
    thead.appendChild(th);
    
    
    for(let val of json){
        
        let tr= document.createElement("tr");
        
        
        for(var i =1;i<11;i++){
            
            let td =document.createElement("td");
            switch(i){
            case 1: td.innerText= "REIM"+val.reimbursementID;
            break;
            case 2: td.setAttribute("class","text-primar");
            td.innerText= "$ "+val.amount;
            
            break;
            case 3: td.innerText= new Date(val.submitDate).toISOString().slice(0, 10);
            
            break;
            case 4: td.innerText= (val.status=="denied")||(val.resolveDate== null)? "N/A":new Date(val.resolveDate).toISOString().slice(0, 10);
            
            break;
            case 5: td.innerText= val.description;
            
            break;
            case 10:
            td.innerHTML ='<a href="./images.html?reimbID='+val.reimbursementID+'">images</a>';
            
            break;
            case 6: td.innerText= val.author;
            
            break;
            case 7: td.innerText= (val.status=="denied")||(val.resolver==null)? "N/A" : val.resolver
            
            break;
            case 8: td.innerText= val.status;
            
            break;
            case 9: td.innerText= val.type;
            
            break;
            }
            tr.append(td);
        }
        
        tbody.append(tr);
    }
    
    
    table.appendChild(thead);
    table.appendChild(tbody);
    cont.appendChild(table);
    //swal("Hello world!");
    
}


////////////////////
function FilteredTable(json,stat){
    let cont= document.getElementById("container");
    cont.innerHTML="";
    let table= document.createElement("table");
    let thead= document.createElement("thead");
    let tbody= document.createElement("tbody");
    table.setAttribute("class", "table table-striped");
    thead.setAttribute("class","text-primary");
    
    let th= document.createElement("tr");
    for(var i =1;i<11;i++){
       
        let td =document.createElement("td");
        switch(i){
        case 1: td.innerText= "ID";
        
        break;
        case 2: td.innerText= "Amount";
        
        break;
        case 3: td.innerText= "Submittion Date";
        
        break;
        case 4: td.innerText= "Resolve Date";
        
        break;
        case 5: td.innerText= "Description";
        
        break;
        case 10: td.innerHTML= '<i class="material-icons">insert_photo</i>';
        
        break;
        case 6: td.innerText= "Author";
        
        break;
        case 7: td.innerText= "Resolver";
        
        break;
        case 8: td.innerText= "Status";
        
        break;
        case 9: td.innerText= "Type";
        
        break;
        }
        th.appendChild(td);
    }
    thead.appendChild(th);
    
    
    for(let val of json){
        if(val.status!=stat){
            continue;
        }
        let tr= document.createElement("tr");
        
        
        for(var i =1;i<11;i++){
            
            let td =document.createElement("td");
            switch(i){
            case 1: td.innerText= "REIM"+val.reimbursementID;
            break;
            case 2: td.setAttribute("class","text-primar");
            td.innerText= "$ "+val.amount;
            
            break;
            case 3: td.innerText= new Date(val.submitDate).toISOString().slice(0, 10);
            
            break;
            case 4: td.innerText= (val.status=="denied")||(val.resolveDate== null)? "N/A":new Date(val.resolveDate).toISOString().slice(0, 10);
            
            break;
            case 5: td.innerText= val.description;
            
            break;
            case 10:
            td.innerHTML ='<a href="./images.html?reimbID='+val.reimbursementID+'">images</a>';
            
            break;
            case 6: td.innerText= val.author;
            
            break;
            case 7: td.innerText= (val.status=="denied")||(val.resolver==null)? "N/A" : val.resolver;
            
            break;
            case 8: td.innerText= val.status;
            
            break;
            case 9: td.innerText= val.type;
            
            break;
            }
            tr.append(td);
        }
        
        tbody.append(tr);
    }
    
    
    table.appendChild(thead);
    table.appendChild(tbody);
    cont.appendChild(table);
    //swal("Hello world!");
    
}


///////////////////////////////////////////////////////

let r1 = document.getElementById("r1");
let r2 = document.getElementById("r2");
let r3 = document.getElementById("r3");
let r4 = document.getElementById("r4");
r1.addEventListener('click', function() {
    //all
    allTable(Reimb);

    
  });
  r2.addEventListener('click', function() {
      //Approved
      FilteredTable(Reimb,"approved");

    
});
r3.addEventListener('click', function() {
    //Denied
    FilteredTable(Reimb,"denied");

    
});
r4.addEventListener('click', function() {
    //Pending
    FilteredTable(Reimb,"pending")

    
});
