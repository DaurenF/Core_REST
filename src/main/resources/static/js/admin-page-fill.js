const tbody = document.getElementById("tbody");
const url = '/admin/users';

async function getAdminPage() {
    let response = await fetch(url);
    if (response.ok) {
        let usersJSONData =
            await response.json().then(usersJSONData => fillPage(usersJSONData, tbody))
    } else {
        alert(`HTTP Error, ${response.status}`)
    }
}
async function getCurrentUser() {
    let response = await fetch('/admin/current-user');
    if (response.ok) {
        let usersJSONData =
            await response.json().then(usersJSONData => fillLoggedUser(usersJSONData, document.getElementById("user_role")))
    } else {
        alert(`HTTP Error, ${response.status}`)
    }
}
function fillLoggedUser(userData, logged){
    $(logged).empty();
    userData.forEach(user => {
        let roleNames = [];

        user.roles.forEach(role => roleNames.push(" " + role.name.toString()
            .replaceAll('ROLE_', '')));

        logged.innerHTML ='logged as: '+ user.username + 'with roles: ' + roleNames.toString();
    })
}
function fillPage(userData, tbody) {
    $(tbody).empty();
    userData.forEach(user => {
        let roleNames = [];


        user.roles.forEach(role => roleNames.push(" " + role.name.toString()
            .replaceAll('ROLE_', '')));

        const tRow = document.createElement("tr");
        tRow.innerHTML =
            `<td class = "text-center">${user.id}</td>
             <td class = "text-center">${user.firstName}</td>
             <td class = "text-center">${user.lastName}</td>
             <td class = "text-center">${user.age}</td>
             <td class = "text-center">${roleNames}</td>
             <td class = "text-center">
                 <button class="btn btn-primary btn-sm border rounded border-success"
                         type="submit"
                         style="width: 75.0312px;padding-top: 6px;background: rgba(32,149,135,0.76);"
                         data-bs-toggle="modal"
                         data-bs-target="#editModal"
                         onclick="editFormFill(${user.id})">Edit
                 </button>
             </td>
             <td class="text-center">
                   <button class="btn btn-primary btn-sm border rounded border-success"
                           type="button"
                           style="width: 75.0312px;padding-top: 6px;background: rgb(220,53,69);"
                           data-bs-toggle="modal" 
                           data-bs-target="#deleteModal"
                           onclick="deleteFormFill(${user.id})"> Delete
                   </button>
            </td>`
        tbody.append(tRow);

    })
}
getCurrentUser();
getAdminPage();