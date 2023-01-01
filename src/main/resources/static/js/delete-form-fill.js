const idDeleteField = document.getElementById('delete-id');
const fistNameDeleteField = document.getElementById('delete-firstName');
const lastNameDeleteField = document.getElementById('delete-lastName');
const ageDeleteField = document.getElementById('delete-age');
const loginDeleteField = document.getElementById('delete-username');

async function deleteFormFill(id) {
    const userByIdURL = '/admin/users/' + id;
    let userResponse = await fetch(userByIdURL);
    if (userResponse.ok) {
        let userJSONData =
            await userResponse.json().then(user => {
                idDeleteField.value = `${user.id}`;
                fistNameDeleteField.value = `${user.firstName}`;
                lastNameDeleteField.value = `${user.lastName}`;
                ageDeleteField.value = `${user.age}`;
                loginDeleteField.value = `${user.username}`;

            })

    } else {
        alert(`HTTP Error, ${userResponse.status}`)
    }
}

async function deleteUser() {
    let url = '/admin/delete-user/' + idDeleteField.value

    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    await fetch(url, method).then(() => {
        $('#delete-close-btn').click();
        getAdminPage();
    })
}