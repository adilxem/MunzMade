console.log("Customers.js");

const baseURL = "http://localhost:8080";

const viewCustomerModal = document.getElementById('view_customer_modal');

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_customer_modal',
  override: true
};

const customerModal = new Modal(viewCustomerModal, options, instanceOptions);

function openCustomerModal () {

    customerModal.show();
}

function closeCustomerModal () {

    customerModal.hide();
}

async function loadCustomerData(id) {

    console.log(id);

    try {
        
        const data = await (await fetch(`http://localhost:8080/user/api/customer/${id}`)).json();
        console.log(data);

        document.querySelector('#customer_name').innerHTML = data.name;
        document.querySelector('#customer_email').innerHTML = data.email;
        document.querySelector('#customer_picture').src = data.picture;
        document.querySelector('#customer_number').innerHTML = data.phoneNumber;
        

        openCustomerModal();

    } catch (error) {
        
        console.error();
        
    }

}

async function deleteCustomer(id) {
    
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!", 
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
      }).then((result) => {
        if (result.isConfirmed) {

            const url = "http://localhost:8080/user/customers/delete/" + id;
            window.location.replace(url);

          Swal.fire({
            title: "Deleted!",
            text: "Your file has been deleted.",
            icon: "success"
          });
        }
      });
}