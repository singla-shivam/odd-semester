const Sequelize = require('sequelize');
const sequelize = new Sequelize('lab', 'root', '', {
    host: 'localhost',
    dialect: 'mysql'
})

sequelize
    .authenticate()
    .then(() => {
        console.log('Connection has been established successfully.');
    })
    .catch(err => {
        console.error('Unable to connect to the database:', err);
    });

const Student = sequelize.define('students', {
    firstName: {
        type: Sequelize.STRING,
        allowNull: false
    },
    lastName: {
        type: Sequelize.STRING,
        allowNull: false
    },
    phone: {
        type: Sequelize.STRING,
        allowNull: false
    },
    email: {
        type: Sequelize.STRING,
        allowNull: false
    },
    rollNo: {
        type: Sequelize.STRING,
        allowNull: false
    },
    dep: {
        type: Sequelize.STRING,
        allowNull: false
    },
})

// Student.sync({ force: true }).then(() => {});

module.exports = {
    sequelize,
    Student
}