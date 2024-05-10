import React, { useState } from 'react';
import './AddProduct.css'; // Make sure to create a corresponding CSS file

const AddProductForm = () => {
  const [product, setProduct] = useState({
    itemName: '',
    cost: '',
    location: '',
    fromDate: '',
    toDate:'',
    description: ''
  });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setProduct({ ...product, [name]: value });
  };

  const handleImageChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      setProduct({ ...product, image: event.target.files[0] });
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Prepare form data to be submitted
    const formData = new FormData();
    for (const key in product) {
      formData.append(key, product[key]);
    }
    console.log('Product to add:', product);
}

  return (
    <div className="add-product-form-container">
      <h2>Tell us what you want to rent today!</h2>
      <form onSubmit={handleSubmit} className="add-product-form">
        <input 
          type="text"
          name="itemName"
          value={product.itemName}
          onChange={handleInputChange}
          placeholder="Enter the item name"
        />
        <input 
          type="text"
          name="cost"
          value={product.cost}
          onChange={handleInputChange}
          placeholder="Cost"
        />
        <input 
          type="text"
          name="location"
          value={product.location}
          onChange={handleInputChange}
          placeholder="Location"
        />
        <div className="date-range">
          <input 
            type="date"
            name="fromDate"
            value={product.fromDate}
            onChange={handleInputChange}
            placeholder="From Date"
          />
          <input 
            type="date"
            name="toDate"
            value={product.toDate}
            onChange={handleInputChange}
            placeholder="To Date"
          />
        </div>
        <textarea 
          name="description"
          value={product.description}
          onChange={handleInputChange}
          placeholder="Enter the Description of the Product"
        />
        <div className="image-upload-container">
          <label htmlFor="image-upload" className="image-upload-label">
            Add image of the item
            <input 
              id="image-upload"
              type="file"
              name="image"
              onChange={handleImageChange}
              className="image-upload-input"
            />
          </label>
        </div>

        <button type="submit" className="submit-button">Rent Now!</button>
      </form>
    </div>
  );
};

export default AddProductForm;
