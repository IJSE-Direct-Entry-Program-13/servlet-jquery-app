import $ from 'jquery';

loadAllCustomers();

function loadAllCustomers() {

    // 1.
    const xhr = new XMLHttpRequest();

    // 2.
    xhr.addEventListener('loadend', () => {
        setTimeout(() => {
            $("#loader-wrapper").hide();
            if (xhr.status === 200) {
                const customerList = JSON.parse(xhr.responseText);
                if (customerList.length) {
                    $("#tbl-customers tfoot").addClass('d-none');
                    customerList.forEach(c =>{
                        $('#tbl-customers tbody').append(`
                    <tr class="animate__animated animate__fadeIn">
                        <td>
                            <div class="profile-picture rounded border"
                                 style="background-image: url('${c.profilePicture}')"></div>
                        </td>
                        <td>
                            <div>
                                <b>ID:</b> <span class="c-id">${c.id}</span>
                            </div>
                            <div>
                                <b>Name:</b> ${c.name}
                            </div>
                            <div>
                                <b>Address:</b> ${c.address}
                            </div>
                        </td>
                        <td class="align-middle text-end">
                            <i title="Delete" class="bi bi-trash fs-2 pe-2"></i>
                        </td>
                    </tr>                        
                        `);
                    });
                } else {
                    $("#tbl-customers tfoot").removeClass('d-none');
                }
            } else {
                alert('Something went wrong, try again');
            }
        }, 500);
    });

    // 3.
    xhr.open('GET',
        'http://localhost:8080/app/v1/customers', true);
    // 4.

    // 5.
    xhr.send();

}

$('#tbl-customers tbody').on('click', ".bi.bi-trash", (e)=>{
    const id = $(e.target).parents("tr").find('span.c-id').text();

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('loadend', ()=>{

    });
    xhr.open('DELETE',
        `http://localhost:8080/app/v1/customers/${id}`,
        true);
    xhr.send();
});