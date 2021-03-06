import React , { useState  } from 'react';
import { DataGrid, useGridApiRef, GridToolbar , GridRowModes} from '@mui/x-data-grid';

import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';

import Badge from '@mui/material/Badge';
import MailIcon from '@mui/icons-material/Mail';

import DeleteIcon from '@mui/icons-material/Delete';
import SendIcon from '@mui/icons-material/Send';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import FavoriteIcon from '@mui/icons-material/Favorite';
import NavigationIcon from '@mui/icons-material/Navigation';
import Switch from '@mui/material/Switch';
import Divider from '@mui/material/Divider';
import Chip from '@mui/material/Chip';

import FormLabel from '@mui/material/FormLabel';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Checkbox from '@mui/material/Checkbox';
import Autocomplete from '@mui/material/Autocomplete';

import Footer from "../../common/Footer";

import PageHeader from '../../common/PageHeader';

const NewTask = () => {

  interface CityOptionType {
    title: string;
    code: string;
  }

  interface CountryOptionType {
    code: string;
    name: string;
    phone: string;
  }

  const cities = [
    { title: 'Kolkata', code: "KOL" },
    { title: 'London', code: "LON" },
    { title: 'California', code: "CAL" }
  ]; 

  const countries = [
    { code: "IN" , "name": "India", "phone": "+91"},
    { code: "USA" , "name": "United States of America", "phone": "+001"},
    { code: "UK" , "name": "United Kingdom", "phone": "+44"},
  ]; 

  const defaultCityProps = {
    options: cities,
    getOptionLabel: (option: CityOptionType) => option.title,
  }; 

  const defaultCountryProps = {
    options: countries,
    getOptionLabel: (option: CountryOptionType) => option.name + ' '+ option.phone,
  };

  return (

    <div id="layoutDrawer_content">
        
        <main>
            
            <div className="container-xl p-5">
                
            <PageHeader heading="New Task" subheading="Create a new task"/>

            <div className="card card-raised">          

            <React.Fragment>
                
                <Grid container>

                  <Grid item xs={12} sm={6} padding={2}>
                    <TextField
                      required
                      id="firstName"
                      name="firstName"
                      label="First name"
                      fullWidth
                      autoComplete="given-name"
                      variant="standard"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6} padding={2}>
                    <TextField
                      required
                      id="lastName"
                      name="lastName"
                      label="Last name"
                      fullWidth
                      autoComplete="family-name"
                      variant="standard"
                    />
                  </Grid>
                  <Grid item xs={12} sm={6} padding={2}>
                    <FormLabel id="lblGender">Gender</FormLabel>
                    <RadioGroup row id="gender">
                      <FormControlLabel value="female" control={<Radio />} label="Female" />
                      <FormControlLabel value="male" control={<Radio />} label="Male" />
                      <FormControlLabel value="disabled" disabled control={<Radio />} label="other"/>
                    </RadioGroup>
                  </Grid>
                  <Grid item xs={12} sm={6} padding={2}>
                    <FormLabel id="lblGender">Opt-In Preference</FormLabel><br/>
                    <FormControlLabel 
                      label="Opting-In"
                      control={
                        <Switch                          
                          checked={true}
                          onChange={()=> alert('Changed')}
                          inputProps={{ 'aria-label': 'controlled' }}
                        />
                      }
                    />                      
                  </Grid>

                  <Grid item xs={12} sm={12} padding={2}>      
                    <Divider textAlign="center">
                      <Chip label="Address" />
                    </Divider>
                  </Grid>

                  <Grid item xs={12} padding={2}>
                    <TextField
                      required
                      id="address1"
                      name="address1"
                      label="Address line 1"
                      fullWidth
                      autoComplete="shipping address-line1"
                      variant="standard"
                    />
                  </Grid>
                  
                  <Grid item xs={12} sm={6} padding={2}>
                    <Autocomplete
                      {...defaultCityProps}
                      id="cities"
                      renderInput={(params) => (
                        <TextField {...params} label="City" variant="standard" />
                      )}
                    />            
                  </Grid>
                  <Grid item xs={12} sm={6} padding={2}>
                    <Autocomplete
                      {...defaultCountryProps}
                      id="countries"
                      renderInput={(params) => (
                        <TextField {...params} label="Country" variant="standard" />
                      )}
                    />            
                  </Grid>
                  <Grid item xs={12} padding={2}>
                    <FormControlLabel
                      control={<Checkbox color="secondary" name="saveAddress" value="yes" />}
                      label="Use this address for payment details"
                    />
                  </Grid>

                  <Grid item xs={12} sm={6} padding={2}>
                      <div style={{float: 'right'}}>    
                        <Button variant="contained" size="medium" endIcon={<AddIcon />} onClick={() => {alert('Form submitted');}}>Save</Button>
                      </div>  
                  </Grid> 
                  <Grid item xs={12} sm={6} padding={2}>
                        <Button variant="outlined" size="medium" endIcon={<DeleteIcon />} color="success" onClick={() => {alert('Form is cleared');}}>Clear</Button>
                  </Grid>        

                </Grid>
              </React.Fragment>

              </div>

            </div>
        </main>

        <Footer/>  
    </div>
    
    );
  };
  
export default NewTask;