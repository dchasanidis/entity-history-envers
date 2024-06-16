import React, {useState, useEffect} from 'react';
import {UserEntity} from "../../models/UserEntity";


interface Props {
    refresh: boolean,
    setRefresh: (b: boolean) => void;
}

const GetUsersComponent = ({refresh, setRefresh}: Props) => {
    const [users, setUsers] = useState<UserEntity[] | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (refresh) {
            const fetchData = async () => {
                const response = await fetch('http://localhost:8080/users');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const result: UserEntity[] = await response.json();
                setUsers(result);
                setLoading(false);
            };
            fetchData();
            setRefresh(false);
        }
    }, [refresh, setRefresh]);

    if (loading) {
        return <div>Loading...</div>;
    }


    return (
        <div className="scrollable-container">
            {users &&
                users.map(user => (
                    <div key={user.id}>
                    <pre>
                        Id: {user.id}<br/>
                        Name: {user.name}<br/>
                        Email: {user.email}<br/>
                        Address: {user.address}<br/><br/>
                    </pre>
                    </div>
                ))
            }
        </div>
    )
}

export default GetUsersComponent;
