(function($) {
  "use strict";
    jQuery(document).ready(function(){       




jQuery(document).ready(function(){    
    var aeolusPortfP = new AeolusPortfolioPlugin();
    aeolusPortfP.init();
});

function AeolusPortfolioPlugin(){
    var isMobile;
    this.init = function(){
        isMobile = (jQuery('.rx_parallax').attr('data-isMobile')=="true")?true:false;
        if(isMobile){handleParalaxMobile();};
        positionHoversColumns();
        handleParalaxContainersSize();
        handlePortfolioRX();
        handleHoversColumns();
        jQuery(window).resize(function(){
           resize_handler();
        });
        handlePortfolioSingle();        
    }
    
    
    
    //handle portfolio single
    function handlePortfolioSingle(){
       //related
       var related_overlay_height = 70;
       jQuery('.rx_related_post').each(function(indx){
           jQuery(this).hover(function(e){                              
                jQuery(this).find('.rx_related_post_overlay').animate({
                    height: related_overlay_height,
                    top: jQuery(this).find('.rx_related_post_overlay').parent().height()/2-related_overlay_height/2    
                }, 200);                             
           }, function(e){
                jQuery(this).find('.rx_related_post_overlay').animate({
                    height: 1,
                    top: jQuery(this).find('.rx_related_post_overlay').parent().height()/2-1/2
                }, 200); 
           });
           jQuery(this).find('.rx_related_post_overlay').css('top', jQuery(this).find('.rx_related_post_overlay').parent().height()/2-1/2+'px');
           jQuery(this).click(function(e){
               e.preventDefault();
                 var url = jQuery(this).attr('data-url');
                 try{
                     window.location = url;
                 }catch(e){}               
           });
       });
       
        
        var thumbWidth = 220;                
        if(jQuery('.portfolioSingleContent').attr('data-isPortfolio')==undefined){
            return;
        }                                          
        //jQuery('html,body, #rx_wrapper').addClass('fullHeightClass');
        
        
        var imagesContainer = jQuery('.portfolioSingleFeaturedImages');
        var featuredImagesAC = new Array();        
        jQuery('.rx_featuredImage').each(function(indx){
            featuredImagesAC.push(jQuery(this).attr('data-image'));
        });        
        jQuery('#featuredimages').remove();
        
        //featured thumbs
        var featuredThumbsUIAC = new Array();
        var navContentWDTH = 0;
        var selectedPortfolioThumbIndx;
        jQuery('.portfolioThumbItem').each(function(indx){
            featuredThumbsUIAC.push(jQuery(this));
            navContentWDTH += thumbWidth;
            jQuery(this).click(function(e){
                e.preventDefault();
                loadPortfolioImage(indx);
            });
            
            jQuery(this).hover(function(e){
                if(selectedPortfolioThumbIndx==indx){
                    return;
                }
                jQuery(this).find('.r_polly').animate({
                    opacity: .7                    
                }, 200);

                jQuery(this).find('.polly_image').animate({
                    opacity: 1                   
                }, 200);                
            }, function(e){
                if(selectedPortfolioThumbIndx==indx){
                    return;
                }  
                jQuery(this).find('.r_polly').animate({
                    opacity: .2                    
                }, 300);

                jQuery(this).find('.polly_image').animate({
                    opacity: .8                  
                }, 300);                              
            });
        });        
        jQuery('.navContent').css('width', navContentWDTH+'px');
        
            jQuery('.portfolioSingleFeaturedImages').bind('contextmenu', function(e) {
                return false;
            });           
        
        //load portfolio image
        function loadPortfolioImage(index){
            if(featuredImagesAC[index]==undefined || featuredImagesAC[index]==null){
                return;
            }            
            jQuery('.portfolioSingleFeaturedImages').backstretch(featuredImagesAC[index], {speed: 800});
            for(var i=0;i<featuredThumbsUIAC.length;i++){
                featuredThumbsUIAC[i].css('cursor', 'pointer');
                    featuredThumbsUIAC[i].find('.r_polly').css('opacity', .2);
                    featuredThumbsUIAC[i].find('.polly_image').css('opacity', .8);                 
                if(i==index){
                    featuredThumbsUIAC[i].css('cursor', 'default');
                    selectedPortfolioThumbIndx = i;  
                    featuredThumbsUIAC[i].find('.r_polly').css('opacity', .9);
                    featuredThumbsUIAC[i].find('.polly_image').css('opacity', 1);                    
                }
            }            
        }
        
        //handle left-rigth nav
        resize_handler();
        validateResult();
        jQuery('.leftNavControl').click(function(e){
            e.preventDefault();   
            var maskWidth = jQuery('.navContentMask').width();               
            var leftTo = extractNumber(jQuery('.navContent').css('left'))+maskWidth;
                jQuery('.navContent').animate({
                    left: leftTo                  
                }, 600, function(){
                    validateResult();
                });                                
        });
        jQuery('.rightNavControl').click(function(e){            
            e.preventDefault();         
            var maskWidth = jQuery('.navContentMask').width();               
            var leftTo = extractNumber(jQuery('.navContent').css('left'))-maskWidth;             
                jQuery('.navContent').animate({
                    left: leftTo                  
                }, 600, function(){
                    validateResult();
                });                        
        });
        
        jQuery('.thumb_navigation').hover(function(e){
                jQuery(this).find('.r_polly').animate({
                    opacity: 1                   
                }, 300);            

        }, function(e){
                jQuery(this).find('.r_polly').animate({
                    opacity: .6                   
                }, 300);            
        });
        
        function validateResult(){
            var leftTo;
            var maskWidth = jQuery('.navContentMask').width();   
            var contentLeftPosition = extractNumber(jQuery('.navContent').css('left'));
            jQuery('.rightNavControl').css('visibility', 'visible');
            jQuery('.leftNavControl').css('visibility', 'visible');
            
            if((contentLeftPosition+jQuery('.navContent').width()) <= maskWidth){
                //adjust right - invalidate right
                jQuery('.rightNavControl').css('visibility', 'hidden');
                //jQuery('.navContent').css('left', -(jQuery('.navContent').width()-maskWidth)+'px');
                leftTo = -(jQuery('.navContent').width()-maskWidth);

                jQuery('.navContent').animate({
                    left: leftTo                  
                }, 300);
            }
            if(contentLeftPosition>=0){
                jQuery('.leftNavControl').css('visibility', 'hidden');
                //jQuery('.navContent').css('left', '0px');
                jQuery('.navContent').animate({
                    left: 0                  
                }, 300);
            }
            if(jQuery('.navContentMask').width()>jQuery('.navContent').width()){
                //invalidate both
                jQuery('.leftNavControl').css('visibility', 'hidden');
                jQuery('.rightNavControl').css('visibility', 'hidden');
            }
            
        }        
        
        if(featuredImagesAC.length>0){
            loadPortfolioImage(0);
        }
    }    
    
    
    /**
     * Utils
     */ 
    //extract number
    function extractNumber(pxValue){        
        if(pxValue==undefined){
             return 0;
        }
        var striped = pxValue.substring(0, pxValue.length-2);
        var val = parseFloat(striped);
        return val;
    }     
    
    
    //handle paralax for mobiles
    function handleParalaxMobile(){
        jQuery('.rxParalaxItem').each(function(indx){
           var backUrl = jQuery(this).attr('data-back');
           jQuery(this).backstretch(backUrl);            
        });        
    }
    
    
    function handlePortfolioRX(){
       //parallax hover
        jQuery('.openPortfolio').each(function(indx){
            jQuery(this).hover(function(e){
                jQuery(this).parent().find('.permalinkFill').animate({ "fill-opacity": "0.9" }, 200);                                        
            }, function(e){
                jQuery(this).parent().find('.permalinkFill').animate({ "fill-opacity": "0" }, 200);
            });
        });
        
             //portfolio side nav
             var postsAC = new Array();
             jQuery('.rxParalaxItem').each(function(indx){
                 var pozition = jQuery(this).offset();
                 postsAC.push({object: jQuery(this), position: pozition});                 
             });        
             var navPoints = new Array();
             jQuery('.portfolioNavPoint').each(function(indx){
                 navPoints.push({object: jQuery(this)});
                 jQuery(this).css('opacity', .5);
                 jQuery(this).css('cursor', 'pointer');
                 jQuery(this).click(function(e){
                     e.preventDefault();                     
                     gotoProject(indx);  
                     jQuery(this).css('opacity', 1);                   
                     return;
                 });
             }); 
             //go to project
             function gotoProject(index){ 
                restrictScrollOperation = true;                               
                jQuery('html,body').animate({
                     scrollTop: (postsAC[index].position.top-jQuery('#headerUI').height())
                }, 800, function onComplete(){
                    restrictScrollOperation = false;                                        
                });
                selectNavPoint(index);
             } 
             
             var restrictScrollOperation = false;
             jQuery(window).scroll(function(){
                  if(restrictScrollOperation){
                      return;
                  }                  
                  for(var i=0;i<postsAC.length;i++){
                      var top = postsAC[i].position.top-70;
                      if(jQuery(this).scrollTop() > top-150 && jQuery(this).scrollTop() < top+150 ){
                          selectNavPoint(i);
                          break;
                      }
                  }  
              });             
            
            function selectNavPoint(index){
                for(var i=0;i<navPoints.length;i++){
                    navPoints[i].object.css('opacity', .5);
                    navPoints[i].object.css('cursor', 'pointer');
                    if(i==index){
                        navPoints[i].object.css('opacity', 1);
                        navPoints[i].object.css('cursor', 'default');
                        try{
                            var top_dif = postsAC[0].object.offset().top;
                            //jQuery('.portfolioSideNav').css('top', (postsAC[i].object.offset().top-top_dif)+100+'px');
                            jQuery('.portfolioSideNav').stop().animate({top: (postsAC[i].object.offset().top-top_dif)+100+'px'}, 800);
                        }catch(e){};                                                
                    }
                }
            }
            try{
            selectNavPoint(0);
            }catch(e){}                             
    }
    
    //parallax item size
    function handleParalaxContainersSize(){        
        jQuery('.rxParalaxItem').each(function(indx){
            jQuery(this).css('height', jQuery(window).height()+'px');
        });
        
       jQuery('.portfolioExcerptTriangleUI').each(function(indx){
            jQuery(this).css('left', (jQuery(this).parent().width()/2-jQuery(this).width()/2)+'px');
       });
       
       jQuery('.portfolioItemBottomUI').each(function(indx){
           jQuery(this).css('left', (jQuery(this).parent().width()/2-jQuery(this).width()/2)+'px');
       });       
       
       jQuery('.portfolioTitleUI').each(function(indx){
           jQuery(this).css('left', (jQuery(this).parent().width()/2-jQuery(this).width()/2)+'px');
       });                
     
    }
    
    //position column hovers
    function positionHoversColumns(){
        jQuery('.rx_hoverui').each(function(indx){
            jQuery(this).css('top', jQuery(this).parent().height()/2-jQuery(this).height()/2+'px');
        });
    }
    
    //handle column hovers
    function handleHoversColumns(){
        jQuery('.rx_thumb_ui').each(function(indx){
            jQuery(this).find('.rx_hoverui').click(function(e){
                e.preventDefault();                
                window.location.href = jQuery(this).attr('data-url');
            });
            jQuery(this).hover(function(e){ 
                jQuery(this).find('.rx_hoverui').animate({
                  height: 70,
                  top: jQuery(this).height()/2-70/2
                }, 200);                           
                //TweenMax.to(jQuery(this).find('.rx_hoverui'), .2, {css:{'height':70}, ease:Power4.EaseIn});
                //TweenMax.to(jQuery(this).find('.rx_hoverui'), .2, {css:{'top':jQuery(this).height()/2-70/2}, ease:Power4.EaseIn});                
            }, function(e){
                jQuery(this).find('.rx_hoverui').animate({
                  height: 1,
                  top: jQuery(this).height()/2-1/2
                }, 200);              
                //TweenMax.to(jQuery(this).find('.rx_hoverui'), .2, {css:{'height':1}, ease:Power4.EaseIn});
                //TweenMax.to(jQuery(this).find('.rx_hoverui'), .2, {css:{'top':jQuery(this).height()/2-1/2}, ease:Power4.EaseIn}); 
            });
        });        
    }
    
    function resize_handler(){
        positionHoversColumns();
        handleParalaxContainersSize();
        jQuery('.navContentMask').css('width', jQuery('.navContentMask').parent().width()-80+'px');
    }
}






    });
})(jQuery);



