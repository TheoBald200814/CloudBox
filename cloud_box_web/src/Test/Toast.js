import React from 'react';
import AlertConfirm, { Button } from 'react-alert-confirm';
import SignInAndSignUp from "../Component/SignInAndSignUp";

const CustomPopup = () => {
    const open = async () => {
        const [action] = await AlertConfirm({
            maskClosable: true,
            custom: dispatch => (
                // <div className="custom-popup">
                //     <div>Custom popup</div>
                //     <div style={{ marginTop: 10 }}>
                //         <button onClick={() => dispatch(false)}>Close</button>
                //     </div>
                // </div>
                <div>
                    <SignInAndSignUp />
                </div>
            )
        });
        console.log(action);
    };

    return (
        <div>
            <Button onClick={open}>Custom Popup</Button>
        </div>
    );
};

export default CustomPopup;