import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';
import Axios from 'axios'; 
function EntityAllView(props) {
    const [customer, setCustomer] = useState([]);  
    const [columnheaderData, setColumnheaderData] = useState([]);
    const [columnnameData, setColumnnameData] = useState([]);
    useEffect(() => { 
        setColumnheaderData(props.Formprops.column_header_data);
        setColumnnameData(props.Formprops.column_name_data);
        const requestbody = {
            service:props.Formprops.service,    
            operation:props.Formprops.extract_operation,
                requesttype:'get',
                requestbody:''
            };
            Axios.post('/invoke', requestbody)
                  .then(response => setCustomer(response.data));             

    }, []); 
    const handleViewClick = (id)=> {
        console.log('id clicked:'+ id);
        props.handleViewClick("SingleCustomerView",id);
      } 
      const handleEditClick = (id)=> {
        console.log('id clicked:'+ id);
        props.handleEditClick("EditForm",id);
      } 
      const handleEntityCreateClick = (page)=> {
        console.log('page1 clicked:'+ page);
        props.handleCreateClick(page);
      } 
    return (
        <React.Fragment>
            <div class="row">
                <div class="col-sm">
                <h3>{props.Formprops.form_title}</h3>
                </div>
                <div class="col-sm"></div>
                <div class="col-sm"></div>
                
                <div class="col-sm">
                <button type="button" class="btn btn-primary" onClick={()=>handleEntityCreateClick("EntityCreateForm")}>{props.Formprops.form_create_header}</button>
                </div>
            </div>
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        {columnheaderData.map(item => {  
                                return (
                                    <th scope="col">{item}</th>
                                )
                        })} 
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                        {customer.map(item => {  
                            return (
                                <tr key={item[props.Formprops.column_id_column]}>
                                    {columnnameData.map(column => {  
                                        return (
                                            <td>{item[column]}</td>
                                        )
                                    })} 
                                    <td><a href="#" onClick={()=>handleViewClick(item[props.Formprops.column_id_column])}>View</a> 
                                    | <a href="#" onClick={()=>handleEditClick(item[props.Formprops.column_id_column])}>Edit</a></td>
                                </tr>
                            )
                        })}  
                </tbody>
            </table>
        </React.Fragment>
      );
}
export default EntityAllView;