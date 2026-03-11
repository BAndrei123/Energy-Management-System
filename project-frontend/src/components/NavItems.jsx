const getNavItems = () => {
  
  const role = localStorage.getItem("role"); // Get role from localStorage
  const isLoggedIn = role !== null; // Check if user is logged in

  const devicesPath = role === "ADMIN" ? "./devicesAdmin" : "./devices";
  const chatPath = role === "ADMIN" ? "./chatAdmin" : "./chat";
  
  
  // Always include Home
  const navItems = [
    {
      id: 1,
      title: "Home",
      path: "./",
      cName: "nav-item",
    },
  ];

  // Only include Devices and News if the user is logged in
  if (isLoggedIn) {
    navItems.push({
      id: 3,
      title: "Devices",
      path: devicesPath,
      cName: "nav-item",
    },
    );
    // Only include Users if the role is ADMIN
    if (role === "ADMIN") {
      navItems.push({
        id: 2,
        title: "Users",
        path: "./usersAdmin",
        cName: "nav-item",
      },
      {
        id: 3,
        title: "Support",
        path: chatPath,
        cName: "nav-item",
      });
    }
    if(role === "USER"){
      navItems.push({
        id: 2,
        title: "Consumption",
        path: "./devicesConsumption",
        cName: "nav-item",
      },
      {
        id: 3,
        title: "Support",
        path: chatPath,
        cName: "nav-item",
      })
    }
  }

  return navItems;
};

export const navItems = getNavItems();
