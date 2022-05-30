import React , { useState  } from 'react';
import { DataGrid, useGridApiRef, GridToolbar , GridRowModes} from '@mui/x-data-grid';

import TextField from "@mui/material/TextField";

import Footer from "../common/Footer";

const Sample = () => {

  const [recordSelected, setRecordSelected] = useState({});

  const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    {
      field: 'firstName',
      headerName: 'First name',
      width: 150,
      editable: true,
      headerClassName: 'super-app-theme--header',
      headerAlign: 'center',
    },
    {
      field: 'lastName',
      headerName: 'Last name',
      width: 150,
      editable: true,
      headerClassName: 'super-app-theme--header',
      headerAlign: 'center',
    },
    {
      field: 'age',
      headerName: 'Age',
      type: 'number',
      width: 110,
      editable: true,
      headerClassName: 'super-app-theme--header',
      headerAlign: 'center',
    },
    {
      field: 'fullName',
      headerName: 'Full name',
      description: 'This column has a value getter and is not sortable.',
      sortable: false,
      width: 160,
      valueGetter: (params) =>
        `${params.row.firstName || ''} ${params.row.lastName || ''}`,
      headerClassName: 'super-app-theme--header',
      headerAlign: 'center',  
    },
    {
      field: "actionEdit",
      headerName: "Edit",
      sortable: false,
      headerClassName: 'super-app-theme--header',
      headerAlign: 'center',
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation(); // don't select this row after clicking 
          alert("Editing record: "+params.id);
        };
  
        return (
          <button className="btn btn-lg btn-text-black btn-icon" type="button" onClick={onClick}><i className="material-icons">edit</i></button>
        );
      }
    },
    {
      field: "actionDelete",
      headerName: "Remove",
      sortable: false,
      headerClassName: 'super-app-theme--header',
      headerAlign: 'center',
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation(); // don't select this row after clicking  
          alert("Deleting record: "+params.id);
          /*
          const api: GridApi = params.api;
          const thisRow: Record<string, GridCellValue> = {};
          api.getAllColumns().filter((c) => c.field !== "__check__" && !!c).forEach(
              (c) => (thisRow[c.field] = params.getValue(params.id, c.field))
            );
          return alert(JSON.stringify(thisRow, null, 4));
          */
        };
  
        return (
          <button className="btn btn-lg btn-text-black btn-icon" type="button" onClick={onClick}><i className="material-icons">delete</i></button>
        );
      }
    },
  ];
  
  const [rows, setRows] = React.useState(() => [
    { id: 11, lastName: 'Snow', firstName: 'Jon', age: 35 },
    { id: 12, lastName: 'Lannister', firstName: 'Cersei', age: 42 },
    { id: 13, lastName: 'Lannister', firstName: 'Jaime', age: 45 },
    { id: 14, lastName: 'Stark', firstName: 'Arya', age: 16 },
    { id: 15, lastName: 'Targaryen', firstName: 'Daenerys', age: null },
    { id: 16, lastName: 'Melisandre', firstName: null, age: 150 },
    { id: 17, lastName: 'Clifford', firstName: 'Ferrara', age: 44 },
    { id: 18, lastName: 'Frances', firstName: 'Rossini', age: 36 },
    { id: 19, lastName: 'Roxie', firstName: 'Harvey', age: 65 }
  ]);

  return (

    <div id="layoutDrawer_content">
        
        <main>

            <div className="container-xl p-5">

            <div className="card card-raised">
                  <TextField name='txtName' />
            </div> 

                <div className="card card-raised">
                    <div className="card-header bg-primary text-white px-4">
                        <div className="d-flex justify-content-between align-items-center">
                            <div className="me-4">
                                <h2 className="card-title text-white mb-0">Tasks</h2>
                                <div className="card-subtitle">Details and history</div>
                            </div>
                            <div className="d-flex gap-2">
                                <button className="btn btn-lg btn-text-white btn-icon" type="button"><i className="material-icons">download</i></button>
                                <button className="btn btn-lg btn-text-white btn-icon" type="button"><i className="material-icons">print</i></button>
                            </div>
                        </div>
                    </div>
                    <div className="card-body p-4">

                    <DataGrid id="taskList"
                      rows={rows}
                      columns={columns}
                      pageSize={5}
                      rowsPerPageOptions={[5]}
                      checkboxSelection
                      disableSelectionOnClick
                      autoHeight={true}
                      autoWidth={true}
                      rowHeight={25}
                      components={{ Toolbar: GridToolbar }} 
                      onSelectionModelChange={(ids) => setRecordSelected(ids)}
                      density='comfortable'
                      sx={{
                        boxShadow: 3,
                        border: 0,
                        borderColor: 'primary.light',
                        '& .MuiDataGrid-row:hover': {
                          color: 'blue',
                          background: '#E0E0E0'
                        },
                      }}
                    />
                       
                    </div>
                </div>
            </div>
        </main>

        <Footer/>  
    </div>
    
    );
  };
  
export default Sample;