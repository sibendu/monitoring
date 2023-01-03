import React , { useState , useEffect } from 'react';

import Footer from "../../common/Footer";
import PageHeader from '../../common/PageHeader';

import axios from "axios";

import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import Divider from '@mui/material/Divider';
import Chip from '@mui/material/Chip';
import FormLabel from '@mui/material/FormLabel';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import TextareaAutosize from '@mui/base/TextareaAutosize';
import Autocomplete from '@mui/material/Autocomplete';

const NewTask = (props) => {

  const [id, setId] = React.useState("");
  const [code, setCode] = React.useState("");
  const [version, setVersion] = React.useState("");
  const [status, setStatus] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [definition, setDefinition] = React.useState("");

  const [message, setMessage] = React.useState("");

  const statusCodes = [
    { title: "DRAFT", code: "DRAFT" },
    { title: 'Published', code: "PUBLISHED" },
    { title: 'Deactivated', code: "DEACTIVATED" }
  ]; 

  interface StatusOptionType {
    title: string;
    code: string;
  }

  const defaultStatusProps = {
    options: statusCodes,
    getOptionLabel: (option) => option.title,
    isOptionEqualToValue: (option, value) => (option.code == value.code),
  };

  useEffect(() => {
    //console.log('New Process page');
    //console.log(props.pageDataObject);
    if(props.pageDataObject != null){
      setId(props.pageDataObject.id);
      setCode(props.pageDataObject.code);
      setVersion(props.pageDataObject.version);
      setStatus(props.pageDataObject.status);
      setDescription(props.pageDataObject.description);
      setDefinition(props.pageDataObject.definition);
      console.log('status='+props.pageDataObject.status);
    }else{
      setId("");
      setCode("");
      setVersion("");
      setStatus("DRAFT");
      setDescription("");
      setDefinition("");
    }
  },[props.pageDataObject]);

  function handleSubmit(){
      //console.log('Submitted : '+ code+' , '+version);
      setMessage("");
      if( id == ''){
        console.log('New record to be created');
        const url = 'http://localhost:8080/process/def/'+ code + '/' + version;
        axios.post( url , definition)
          .then(function (response) {
            //console.log(response.data.length);
            console.log(response);
            if(response.status == 200){
              setMessage("Record created successfully"); 
              setId(response.data.id);
            }else{
              setMessage("Service error, Please contact IT Admin. Error Message: "+response.statusText); 
            }    
          })
          .catch(function (error) {
            console.log(error);
            setMessage("Service unavailable, Please contact IT Admin. Error Message: "+error.message); 
          });

      }else{
          console.log('Record being updated: '+definition);
          const payload = {'id': id, 'code': code, 'version': version, 'description': description, 'definition': definition};
          const url = 'http://localhost:8080/process/def/'+ id;
          axios.put( url , payload)
          .then(function (response) {
            //console.log(response.data.length);
            console.log(response);
            if(response.status == 200){
              setMessage("Record updated successfully"); 
              setId(response.data.id);
            }else {
              setMessage("Service Error, Please contact IT Admin. Error Message: "+response.statusText); 
            }    
          })
          .catch(function (error) {
            console.log(error);
            setMessage("Service unavailable, Please contact IT Admin. Error Message: "+error.message); 
          });
      }
  }

  return (

    <div id="layoutDrawer_content">
        
        <main>
            <PageHeader heading="Process Definition" subheading="Add/ Update a Process Definition"/>

            <div className="card card-raised">          

            <React.Fragment>
                
                <Grid container>

                  {
                    message != ''?                        
                        <Grid item xs={12} sm={12} padding={2}>      
                          <Divider textAlign="center">
                            <Chip label={message} />
                          </Divider>
                        </Grid>
                    :
                        <></>
                  }

                  <Grid item xs={12} sm={12} padding={2}>
                    <FormLabel id='lblId'>Id: {id}</FormLabel>  
                  </Grid>  
                  <Grid item xs={12} sm={6} padding={2}>                    
                    <TextField required id="code" name="code" label="Code" fullWidth variant="standard"
                      value = {code} onChange={(e) => setCode(e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={12} sm={3} padding={2}>
                    <TextField required id="version" name="version" label="Version" fullWidth variant="standard"
                      value = {version} onChange={(e) => setVersion(e.target.value)}
                    />                    
                  </Grid>

                  <Grid item xs={12} sm={3} padding={2}>  
                    <Autocomplete
                      {...defaultStatusProps}
                      renderInput={(params) => (
                        <TextField {...params} label="Status" variant="standard"/>
                      )}
                      onChange={(e) => setStatus(e.target.value)}
                    />            
                  </Grid>

                  <Grid item xs={12} sm={12} padding={2}>
                    <TextField required id="description" name="description" label="Description" fullWidth variant="standard"
                      value = {description} 
                      onChange={(e) => setDescription(e.target.value)}
                    /> 
                  </Grid>
                  <Grid item xs={12} sm={12} padding={2}>
                    <TextareaAutosize id="description" name="description" label="Description" minRows={4} style={{ width: 600 }}
                      value = {definition}
                      onChange={(e) => setDefinition(e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={12} sm={6} padding={2}>
                      <div style={{float: 'right'}}>    
                        <Button variant="contained" size="medium" endIcon={<AddIcon />} onClick={() => handleSubmit() }>Save</Button>
                      </div>  
                  </Grid> 
                  <Grid item xs={12} sm={6} padding={2}>
                        <Button variant="outlined" size="medium" endIcon={<DeleteIcon />} color="success" onClick={() => handleSubmit() }>Clear</Button>
                  </Grid>        

                </Grid>
              </React.Fragment>

              </div>

        </main>

        <Footer/>  
    </div>
    
    );
  };
  
export default NewTask;