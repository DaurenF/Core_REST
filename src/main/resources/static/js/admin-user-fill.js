const tbody = document.getElementById('tbody');
const url = '/admin/current-user';

async function getAdminPage() {
    let response = await fetch(url);
    if (response.ok) {
        let usersJSONData =
            await response.json().then(usersJSONData => fillPage(usersJSONData, tbody))
    } else {
        alert(`HTTP Error, ${response.status}`)
    }
}
function fillPage(userData, tbody) {
    $(tbody).empty();

    userData.forEach(user => {
        let roleNames = [];

        user.roles.forEach(role => roleNames.push(" " + role.name.toString()
            .replaceAll('ROLE_', '')));
        document.getElementById("user_role").innerHTML='logged as '+user.username +' with roles: ' + roleNames.toString();

        const tRow = document.createElement("tr");
        tRow.innerHTML =
            `
             <td class = "text-center">${user.firstName}</td>
             <td class = "text-center">${user.lastName}</td>
             <td class = "text-center">${user.age}</td>
             <td class = "text-center">${roleNames}</td>`
        tbody.append(tRow);

    })
}

getAdminPage();