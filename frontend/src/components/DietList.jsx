import React from "react";

const DietList = ({ dietList }) => {
  return (
    <div>
      <h5 className="mb-4 text-primary">📅 Diyet Listem</h5>
      {dietList && dietList.length > 0 ? (
        <ul className="list-group">
          {dietList.map((item, index) => (
            <li key={index} className="list-group-item">
              {item}
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-muted">Henüz bir diyet listeniz bulunmamaktadır.</p>
      )}
    </div>
  );
};

export default DietList;
