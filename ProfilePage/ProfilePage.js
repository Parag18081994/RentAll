import React, { useState } from 'react';
import './ProfilePage.css'; // make sure to create a corresponding CSS file
import defaultProfileImg from './Profileimage.png';

const ProfilePage = () => {
  const [profile, setProfile] = useState({
    name: 'Kshiraj Kunta',
    dob: 'Jan 1, 1990', // Example Date of Birth
    address: '123 RentAll St., Rental City, RA 12345',
    email: 'xxxxxxx@rentall.com',
    phone: '(123) 456-7890',
    username: 'Username',
    password: '********', // You would not actually display a real password
    buyerRating: 4.5,
    sellerRating: 4.3,
  });

  // For demonstration purposes only, handleEdit and handleSave don't actually edit or save.
//   const handleEdit = () => {
//     console.log('Edit button clicked');
//   };

//   const handleSave = () => {
//     console.log('Save button clicked');
//   };

  return (
    <div className="profile-page">
      
      <div className="profile-image-container">
        <img src={defaultProfileImg} alt="Profile" className="profile-image" />
      </div>
      
      <div className="profile-info-container">
        <div className="personal-info">
          <h3>Personal Information</h3>
          <p><strong>Name: {profile.name}</strong></p> {/* Name in bold */}
          <p>DOB: {profile.dob}</p>
          <p>Address: {profile.address}</p>
        </div>
        <div className="account-info">
          <h3>Account Information</h3>
          <p>Username: {profile.username}</p>
          <p>Email: {profile.email}</p>
          <p>Password: ********</p> {/* Placeholder for display */}
          <div className="ratings">
            <p>Buyer Rating: {profile.buyerRating}/5</p>
            <p>Seller Rating: {profile.sellerRating}/5</p>
          </div>
        </div>
      </div>
      <div className="profile-actions">
        <button>Edit</button>
        <button>Save</button>
      </div>
    </div>
  );
};

export default ProfilePage;
