import React, {useState} from 'react';

interface FormData {
    name: string;
    email: string;
    address: string;
}

interface Props {
    setRefresh: (b: boolean) => void;
}

const CreateUserFormComponent = ({setRefresh}: Props) => {
        const [formData, setFormData] = useState<FormData>({name: '', email: '', address: ''});
        const [loading, setLoading] = useState(false);
        const [error, setError] = useState<string | null>(null);
        const [success, setSuccess] = useState<boolean>(false);

        const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
            setFormData({
                ...formData,
                [e.target.name]: e.target.value,
            });
        };

        const handleSubmit = async (e: React.FormEvent) => {
            e.preventDefault();
            setLoading(true);
            setError(null);

            try {
                const response = await fetch('http://localhost:8080/users', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(formData),
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                setSuccess(true);
                setFormData({name: '', email: '', address: ''});
            } catch (error) {
                if (error instanceof Error) {
                    setError(error.message);
                } else {
                    setError('An unexpected error occurred');
                }
            } finally {
                setRefresh(true);
                setLoading(false);
            }
        };

        return (
            <div>
                <h1>Submit Your Information</h1>
                {success && <div>Form submitted successfully!</div>}
                {error && <div>Error: {error}</div>}
                <form onSubmit={handleSubmit}>
                    <div>
                        <label>Name:</label>
                        <input type="text" name="name" value={formData.name} onChange={handleChange} required/>
                    </div>
                    <div>
                        <label>Email:</label>
                        <input type="email" name="email" value={formData.email} onChange={handleChange} required/>
                    </div>
                    <div>
                        <label>Address:</label>
                        <input type="text" name="address" value={formData.address} onChange={handleChange} required/>
                    </div>
                    <button type="submit" disabled={loading}>
                        {loading ? 'Submitting...' : 'Submit'}
                    </button>
                </form>
            </div>
        );
    }
;

export default CreateUserFormComponent;
