package com.webtools.finalProject.Controller;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.webtools.finalProject.Dao.NuEventsDao;
import com.webtools.finalProject.Dao.UserDao;
import com.webtools.finalProject.Dao.UserProductDao;
import com.webtools.finalProject.Dao.UserOrderDao;
import com.webtools.finalProject.Dao.UserWishlistDao;
import com.webtools.finalProject.Exception.UserException;
import com.webtools.finalProject.Pojo.NuEvents;
import com.webtools.finalProject.Pojo.User;
import com.webtools.finalProject.Pojo.UserProductMap;
import com.webtools.finalProject.Pojo.UserWishlistMap;
import com.webtools.finalProject.Pojo.UserOrderMap;
import com.webtools.finalProject.Validator.LoginValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
@Controller
public class LoginController {
	
	@Autowired
   private Environment environment;
	
	
	@Autowired
	LoginValidator loginValidator;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	NuEventsDao eventsDao;
	
	@Autowired
	UserProductDao userProdDao;
	
	@Autowired
	UserOrderDao userOrderDao;
	
	@Autowired
	UserWishlistDao userWishlistDao;
	
	
	@GetMapping("/login.htm")
	public ModelAndView handleLogin(ModelMap model, User user) {
		 model.addAttribute("user", user);
		return new ModelAndView("userLogin");
		
	}
	
	@PostMapping("/home.htm")
	public String loginSuccessful(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("user") User user, BindingResult result) throws UserException, IOException {
		
		loginValidator.validate(user, result);
		List<UserProductMap> userProducts = null;
		List<UserOrderMap> userOrders = null;
		List<UserWishlistMap> userWishlistItems = null;
		List<NuEvents> travelPackagesCart = new ArrayList<NuEvents>();
		List<NuEvents> travelPackagesOrders = new ArrayList<NuEvents>();
		List<NuEvents> travelPackagesWishlist = new ArrayList<NuEvents>();
		int optionSelected = 0;
		int count =0 ;
		int updateValue =0;
		if(result.hasErrors()) {
			return "userLogin";
		}else {
		System.out.println("Entered home.htm");
		System.out.println(user.getName());
		User currentUser = userDao.getUser(user.getName());
		if(currentUser!=null) {
			System.out.println(currentUser);
			session.setAttribute("currentUser", currentUser);
			System.out.println(currentUser.getName());
			System.out.println(user.getName());
			System.out.println(currentUser.getPassword());
			System.out.println(user.getPassword());
				if(currentUser.getName().equalsIgnoreCase(user.getName())) {
					if(currentUser.getPassword().equals(user.getPassword())) {
						session.setAttribute("isLoggedIn", true);
						String userAgent = request.getHeader("User-Agent");
						System.out.println(userAgent);
						session.setAttribute("userAgent", userAgent);
						System.out.println("Valid Credentials");
						
						
						List<NuEvents> products =eventsDao.getProducts();
						List<NuEvents> initialProducts = new ArrayList<>();
						for(NuEvents i : products) {
							if(count  < 4) {
								initialProducts.add(i);
								count++;
							}
						}
						
						userProducts = userProdDao.getAllUserProducts(currentUser);
						userOrders = userOrderDao.getAllUserOrders(currentUser);
						userWishlistItems = userWishlistDao.getAllUserWishlistItems(currentUser);
						for(UserProductMap i : userProducts) {
						 System.out.println("userProducts 1: "+ i.getNuEvents().getPackageId());
						 travelPackagesCart.add(eventsDao.getSelectedProduct(i.getNuEvents().getPackageId()));
						 System.out.println("travelPackagesCart------"+travelPackagesCart);
						
						}
						for(UserOrderMap i : userOrders) {
							 System.out.println("userOrders 1: "+ i.getNuEvents().getPackageId());
							 travelPackagesOrders.add(eventsDao.getSelectedProduct(i.getNuEvents().getPackageId()));
							 System.out.println("travelPackagesOrders -----"+travelPackagesOrders);
							
						}
						for(UserWishlistMap i : userWishlistItems) {
							 System.out.println("userWishlistItems 1: "+ i.getNuEvents().getPackageId());
							 travelPackagesWishlist.add(eventsDao.getSelectedProduct(i.getNuEvents().getPackageId()));
							 System.out.println("travelPackagesOrders -----"+travelPackagesWishlist);
							
						}
						session.setAttribute("products", products);
						session.setAttribute("initialProducts", initialProducts);
						session.setAttribute("optionSelected", optionSelected);
						session.setAttribute("updateValue", updateValue);
						session.setAttribute("travelPackagesCart", travelPackagesCart);
						session.setAttribute("travelPackagesOrders", travelPackagesOrders);
						session.setAttribute("travelPackagesWishlist", travelPackagesWishlist);
						
						for (NuEvents i : products) {
							
				            // Print all elements of ArrayList
							request.getSession().setAttribute("product", i);
				            System.out.println(i);
				        }
						return "dashboard1";
				}else {
						user.setName(currentUser.getName());
						user.setPassword("");
						request.setAttribute("passwordAlertMessage", "Please check your Password");
						System.out.println("Please check the Password");
					}
				}
			
			}else {
				user.setName("");
				user.setPassword("");
				request.setAttribute("userNameAlertMessage", "Username doesnot exists");
				System.out.println("Username doesnot exist");
				
			}
	       
			return "userLogin";
		}
	}
	@PostMapping("/logout.htm")
	public String handleLogout(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session, User user) throws Exception
	{
		model.addAttribute("user",user);
		System.out.println("logged in user details from session"+ session.getAttribute("currentUser").toString());
		session.invalidate();
		 
	   
		return "userLogin";
	}
//		
}