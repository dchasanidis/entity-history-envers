import React, {useState} from 'react';
import GetUsersComponent from "./getUserscomponent/GetUsersComponent";
import CreateUserFormComponent from "./createUserFormComponent/CreateUserFormComponent";

const ParentComponent = () => {
    const [refresh, setRefresh] = useState<boolean>(true);
    return (
        <div className="App">
            <div className="split left">
                <div className="content">
                    <GetUsersComponent refresh={refresh} setRefresh={setRefresh}/>
                </div>
            </div>
            <div className="split right">
                <div className="content">
                    <CreateUserFormComponent setRefresh={setRefresh}/>
                </div>
            </div>
        </div>
    );
};

export default ParentComponent;
