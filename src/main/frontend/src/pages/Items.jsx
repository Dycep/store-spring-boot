import axios from "axios";
import React, {useState, useEffect} from 'react';

export const Items = () => {
    const [items, setItems] = useState([]);
  
    const fetchItems = () => {
      axios.get('http://localhost:8080/').then(res => {
      console.log(res);
      setItems(res.data);
      });
    };
  
    useEffect(()=>{
      fetchItems();
    }, []);
  
    return items.map((item) => {
      return (
        <div key={item.id}>
          <h1>{item.name}</h1>
          <p>{item.price}</p>
          <p>{item.description}</p>
        </div>
      )
    });
  };
