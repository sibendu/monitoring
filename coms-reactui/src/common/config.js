export const entity_create = {
    create_customer: {
      form_title: 'Add a new Customer',
      form_name: 'create_customer',
      form_id: '1',
      form_data: {"customer_id":"","account_number":"","first_name":"","last_name":"","company_name":""},
      field_data : [
        { 'field_id': '1','field_name': 'account_number','parent_container':'', 'field_type': 'text', 'field_label': 'Account Number','default_value': 'Account Number' },
        { 'field_id': '2','field_name': 'first_name','parent_container':'', 'field_type': 'text', 'field_label': 'First Name','default_value': 'First Name'},
        { 'field_id': '3','field_name': 'last_name','parent_container':'', 'field_type': 'text', 'field_label': 'Last Name','default_value': 'Last Name'},
        { 'field_id': '4','field_name': 'company_name','parent_container':'', 'field_type': 'text', 'field_label': 'Company','default_value': 'Company'}
      ],
      service: 'COMS-API',
      submit_operation: '/addcustomer'
    },
    register_user: {
      form_title: 'Register',
      form_name: 'register_user',
      form_id: '5',
      form_data: {"first_name":"","last_name":"","login_id":"","user_password":""},
      field_data : [
        { 'field_id': '1','field_name': 'login_id','parent_container':'', 'field_type': 'text', 'field_label': 'Login email','default_value': 'Login email' },
        { 'field_id': '2','field_name': 'first_name','parent_container':'', 'field_type': 'text', 'field_label': 'First Name','default_value': 'First Name'},
        { 'field_id': '3','field_name': 'last_name','parent_container':'', 'field_type': 'text', 'field_label': 'Last Name','default_value': 'Last Name'},
        { 'field_id': '4','field_name': 'user_password','parent_container':'', 'field_type': 'text', 'field_label': 'Password','default_value': 'password'}
      ],
      service: 'COMS-USER-API',
      submit_operation: '/registeruser'
    }
  };

  export const entity_all_view = {
    view_all_customer: {
      form_title: 'View all customer',
      form_name: 'view_all_customer',
      form_id: '2',
      column_header_data: ["Id","Account No","First Name","Last Name","Company"],
      column_name_data: ["customer_id","account_number","first_name","last_name","company_name"],
      column_id_column:"customer_id",
      service: 'COMS-CUSTOMER-API',
      extract_operation: '/getallcustomers',
      form_create_header: 'Create new customer'
    }
  };
  export const entity_single_view = {
    view_single_customer: {
      form_title: 'View customer',
      form_name: 'view_single_customer',
      form_id: '2',
      field_data : [
        { 'field_id': '1','field_name': 'customer_id','field_label': 'Customer Id'},
        { 'field_id': '2','field_name': 'account_number', 'field_label': 'Account Number'},
        { 'field_id': '3','field_name': 'first_name', 'field_label': 'First Name'},
        { 'field_id': '4','field_name': 'last_name','field_label': 'Last Name'},
        { 'field_id': '5','field_name': 'company_name', 'field_label': 'Company'}
      ],
      column_id_column:"customer_id",
      form_edit_header:"Edit customer",
      service: 'COMS-API',
      dataload_operation: '/getcustomerbyid/'
    }
  };
  export const entity_edit = {
    edit_customer: {
      form_title: 'Edit Customer',
      form_name: 'edit_customer',
      form_id: '3',
      form_data: {"customer_id":"","account_number":"","first_name":"","last_name":"","company_name":""},
      field_data : [
        { 'field_id': '1','field_name': 'customer_id','parent_container':'', 'field_type': 'label', 'field_label': 'Customer Id','default_value': 'Customer Id' },
        { 'field_id': '2','field_name': 'account_number','parent_container':'', 'field_type': 'text', 'field_label': 'Account Number','default_value': 'Account Number' },
        { 'field_id': '3','field_name': 'first_name','parent_container':'', 'field_type': 'text', 'field_label': 'First Name','default_value': 'First Name'},
        { 'field_id': '4','field_name': 'last_name','parent_container':'', 'field_type': 'text', 'field_label': 'Last Name','default_value': 'Last Name'},
        { 'field_id': '5','field_name': 'company_name','parent_container':'', 'field_type': 'text', 'field_label': 'Company','default_value': 'Company'}
      ],
      service: 'COMS-API',
      dataload_operation: '/getcustomerbyid/',
      submit_operation: '/addcustomer'
    }
  };