import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';
import Axios from 'axios'; 
import Header from '../../common/Header';
const EntityCreateForm = (props) => {
    const [formData, setFormData] = useState({});
    const [fieldData, setFieldData] = useState([]);
      useEffect(() => {          
        console.log("useEffect 2 called");
        setFormData(props.Formprops.form_data);
        setFieldData(props.Formprops.field_data);
        console.log(formData);
    }, []); 
    const onSubmit = e => {
        e.preventDefault();
        const requestbody = {
            service:props.Formprops.service,    
            operation: props.Formprops.submit_operation,
                requesttype:'post',
                requestbody:JSON.stringify(formData)
            };
        Axios.post('/invoke', requestbody)
        .then(res=>{
        console.log(res);
        console.log(res.data);
        alert(res.data.status);
        });
    };
    const handleChange = e =>{
        formData[e.target.id] = e.target.value;
        setFormData({ ...formData });
      };
    return (
        <React.Fragment>
             <div id="layoutDrawer_content">
        
        <main>
        <div class="container-xl p-5">
            <h3>{props.Formprops.form_title}</h3>
        
            <form class="form-horizontal" onSubmit={onSubmit}>
                {fieldData.map(field => {  
                    switch (field.field_type) {
                        case "text":
                                return (
                                    <div className="form-group">
                                    <label for={field.field_name} className="col-xs-2">{field.field_label}</label>
                                    <div className="col-xs-10">
                                    <input type="text" className="form-control" id={field.field_name} placeholder={field.default_value} onChange={handleChange} />
                                    </div>
                                </div>
                                )
                        case "dropdown":
                            return (
                                <div className="form-group">
                                    <label for={field.field_name} className="col-xs-2">{field.field_label}</label>
                                    <div class="col-xs-10">
                                        <select className="form-select" aria-label="Default select example">
                                            <option selected>Open this select menu</option>
                                            <option value="1">One</option>
                                            <option value="2">Two</option>
                                            <option value="3">Three</option>
                                        </select>
                                    </div>
                                </div>
                            )
                    }
                })
                }
                <div class="col-xs-10 col-xs-offset-2">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
            </div>
            </main>
            </div>
            </React.Fragment>

    );
};
export default EntityCreateForm;