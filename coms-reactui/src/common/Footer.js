import * as React from 'react';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link'

export default function Footer(props) {
    return (
      <div id="layoutDrawer_footer">
      <footer className="py-4 mt-auto border-top">
        <div className="container-xl px-5">
            <div className="d-flex flex-column flex-sm-row align-items-center justify-content-sm-between small">
                <div className="me-sm-2">Copyright © Sibendu Das 2022</div>
                <div className="d-flex ms-sm-2">
                    <a className="text-decoration-none" href="#!">Privacy Policy</a>
                    <div className="mx-1">·</div>
                    <a className="text-decoration-none" href="#!">Terms &amp; Conditions</a>
                </div>
            </div>
        </div>
      </footer>
      </div>
    );
  }