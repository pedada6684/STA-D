package com.klpc.stadspring.util;

import com.klpc.stadspring.domain.advert.service.AdvertService;
import com.klpc.stadspring.domain.advert.service.command.request.AddAdvertRequestCommand;
import com.klpc.stadspring.domain.advertVideo.service.AdvertVideoService;
import com.klpc.stadspring.domain.product.service.ProductServiceImpl;
import com.klpc.stadspring.domain.product.service.command.AddProductCommand;
import com.klpc.stadspring.domain.user.service.UserService;
import com.klpc.stadspring.domain.user.service.command.JoinCompanyUserCommand;
import com.klpc.stadspring.domain.user.service.command.JoinUserCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyGenerator {

    private final UserService userService;
    private final AdvertService advertService;

    /**
     * 유저 생성
     */
    public void createDummyUsers(){
        log.info("creatDummyUsers");
        JoinUserCommand command1 = JoinUserCommand.builder()
                .nickname("king")
                .name("king")
                .email("king@king.com")
                .profileImage("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEBUSExMVFhUXFxcWFhcXGRYWGBgYFRUXFxcVFRgYHSggGBolHRUWITEhJSorLi4uGB8zODMtOCgtLysBCgoKDg0OGxAQGy8lHyItLS0tLS0uMi4tLS0tLS0tLS0tLS04LS0tLysvLS0tLzUtLS0tLSstLS0tLS0tLS0tLf/AABEIALgBEgMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAABQYDBAcCAf/EAE0QAAIBAgMEBgQJCAYKAwAAAAECAAMRBBIhBQYxQRMiUWFxgTKRobEHFEJSYnKCksEjM7LC0dLh8DRDRIOi0xUkU1Rkc5PD4vEXVWP/xAAZAQEAAwEBAAAAAAAAAAAAAAAAAgMEAQX/xAAuEQACAgECBQEIAgMBAAAAAAAAAQIRAxIhBBMxQVHwFCJhgZGhsdFCcTLh8cH/2gAMAwEAAhEDEQA/AO4xEQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREARE+M1uMA+xPgM+wBERAEREAREQCnb64yoalGjTXMWZgLMAS3Rm/EjQBgePIyR3a2y1VbVVKkO1NSxQksnpBgpIU9gudPOam9WHVLVb5XRzVpPbMofJ1kfsBAOpt7JWtzy9bFrnbqrVNcixsaj02sRppcHt5GeW8ko8RXx+xoUU4HUIgRPUM4iIgCIiAIiIAiIgHmo4UEnQAXJ7hOebzbx12FRehrUgjhEOemoY1E6rsVqAg6k5CDw7ZeNs0Q+HqoSRdGFxfTQ66a+qcv3h2j0t0XL+VK1XQ3zB6Y6MhSbXGgNrXmDjcrjUfJdhje51HZlYslmILi2axuNdVI7iCJuSA3OwtZaJeuT0lTKbEAFUVQqggcOBPnJ+a8TbgrK5dRERLCIiIgCIiAIiIAiIgCRu3qYel0ZPpnLwvwVm/VvJKYcVQzrYGxBup42I7uY5eBMhli5QaR1OmUXd/a2KT4tSCUslRWqXLuWKKNUCEWVha3Hs7ZecDi0q01qIbq6hlPaDznNd7MFVpOarDo1U8FZnQFmuSui5FZrA3NgSePCXzdd1OEpZWVlygDKLAAfJIPMcDoNb6DhMPBykpODLsqVKSJWIieiUCIiAIiIBGbfqKtFiwBAV2IPYqG49s5bszaNShUTGLc2Ip1luTcWHbwBA05Bll/wB+6+XC1O9cv33VT7AZzrZVdU0f824y1PAm4cd6nX19s8niptZW12o1Yl7p2XB4latNaiG6sAyntB1maUTcXHNQqvgap5lqJ5G/WIHcR1h9qXuejhyrJBSRnlHS6EREtIiIiAIiIAiJgxuJFNC51twHMkmyqO8kgec42krYNLa2LA6l7C2Zz9HXq+diT3A9onPquMWtiMNiV/NvWNLLqBkDIDcfSuxPdYcp83q2sXHQq1zU69Rh8w24fWsLfQVe0yLpVbUco/q61Nx3BlK+9RPGyZXOet9O39GyEKR2kCfZ5RrgHt19c9T2jGIiIAiIgCV7bm3atKtTp0qHSKzlHbOqlWyZgqg8dNSeWnlYDOa7x4urTrO68DUL03W4QMi2Yi/VYkKAQSNQ1riZOMyuEFp6ssxR1Pcld18c9SstUu2Wq9WmEZmYhqQY27Lcez0BxvLsJSdx92qtJhXrmxAY00vexqWzOQDYEj3mXaT4aLjDc5kavYRETQQEhNrbeGHqqrJ+TNg1S9lQtwzE6WMm5W95qBUF7FrOlVfSK5kAHRvYHKpA42tfj30cRKUYXElBJvc28ZUp4vDVFpOj+kuhDLmXiptoRyI7D3yibH2i2zK63zHB4ixW9yabfNPePaLcwZK7pVqwxaoab5bVQcotSUFy3Wc+mwZWUAdskdo7Jp1RWwtQdRmzqea57sGHeHz+QtPPnlbUcvyZeo1cS2U6gYAg3BFwRzBnqc93F2zUw9ZtnYk9ZT+SY8COVj2HlOhT08WRTjZRKNMRESwiIiIBQvhNxNkCdrD1KpPvcSjrwA7pPfCBis+JC8hf2tb3IPXIEGeLN6pN/Fm2CqKJPD1WekGU/lsNZlI4tRB4DtKG3kROo7B2ouJoJVXmOsOxhxH88iJyPAYg06i1F4qb25EcCp7iCRLVu1jBhMWEB/1bE2amT8ljwU9hv1T9mT4XLy8ml9H69f6IZYWrOhxET1zKIiIAiIgCUPfXbi5zTv1KQJe3Nj1coPab5PA1D8mWTenbAwuHLj026tMdrHnbmBxnIcdUJbITfKc1Q8c1U8r88vDxzH5Uw8Zk20L5l+GHdnkVGYs7nruczfgB2Adk9UqurDtUH7jBv2zATPgOo8x6xMNbGg7lsepmw9I/QX2AAzckHuXXz4KmewEH13/GTk9jDLVji/gYpqpNCIiWERESM25tLoUAW3SNcIDwFuLt9EDX1DnIzmoRcmdSt0au3NojrUw2VVF6z3tlW18gPJiOJGoHeRIXdTHCtUqYtyKdGnajQU2UDNa5twzHqAeNpU96NqX/ACCElQSXJ4s17m/ffU9+nyRJjaeFFCjQw9SnmRKQqOHS6dK9RXLBzorLlddbGzgjhPK5rcnll26GnRS0ruXZNtoa3RAHiVzcswF7ePGSs53ua9arU6NUy0UxFSsW7LhgKKkaHUjhyvOiTfw85ThcijJFJ0hERNBAT4yg6GfYgFZ+JjC1yqDLRq9ZQNBTqqNQo5BgAbdoPbNnaIuVcccpU95HWU+GjeuSW08L0lMrzHWXuZdR+zzkJSxfWVG4N6J7G42PcQCPED508ni4aG4rpL8ovg738ERvjsY4mguIo/n6QzLbiyjUp48x5jnJncjeMYzDgk/lEFnHb9L+fxnnY1WxemeKMR5cRKrtvCvs3GrjKI/I1D+UUcAx1YeBFz3EHukeFzaWmSnG9jqEgdtbVqU6qhBdFy9LYjP+UayZVK9bgSdRx0vJfBYpatNaiG6sLj+PfIveLBEo7pluyqjgg2IDdVgQdGUsT38NNCPR4hy5bcCiFatzPsba4rixVkbKHCNlzZGJCP1SRZgL8e46ib+KrBEZzwVS3qF5RdxKnRYutRqVi75KYUFEQWyipZSNbDPYA34cZP76Y/osK3aeHlY+onKPtSvHmfI1vr6ok4e/SOV7YxGfEOTyNvV1fwJmqKsxVRYAnidZn2bp0lX/AGSZh9diEp+otm+xMCWxqM+HbXzlpwmFFWiaR7cyHsfsvyDe+0ikw/TYRMSPTpWp1vpJotOoe8aKT2W7JYtgpdZny/A7exZ92tomrSCv+cTqt38g3nbXvBkxKlWDUagrL9rsIPEnuPPsNj2yzYPFLVQOpuD6wRoQewg6T1eD4nmx0vqjJkhTtGVmA4m0+hr8JUt7ce1KurNValTRabKQRlcs1RXDqR1rWpW1+VMu4+0qlYPdi6KtMhyVN3cMzhSthlF1tztLVnvLy6OaPd1FpnxmAFzoBqTPspe+m30yvQVuqB+WYdn+zHeTp7ORtZlyLHHUzkYuToq29O3GxFfpV9Fbphx2/OrH3j7PYZBrRyi090nLsajC19FX5q8hMldgBeeO5OTtm1KlSNQieXGnt9WskMKt1emeJQuvc9O7afZzjzmpadsHRfgyxmajUp/Na48D/Ik/tnbYoMi5He5XMUAIpq7ZVd9b2JBAABN+XEjnHwf7Q6HFhSeq/UPnbL7csnd5cR02PNChVCuKKl7rmF6VXRSQQTbpifokerSszhgddV/0olC5lo2NtjpWyNoSgqpw61NjYPpwvppx1kxK1uhu+1AGrUN6rqFtckIgJYICSe2WWbcOrQtXUpnV7GLFYhaaM7myqLk+E5lt/bTEtVOjvog+Yo4erj3sfoyb3u2sHYpmtSpa1D85xyHblJA72I7JzzEYk1XzHS/AdgHAfz3zDxOTXLSui/JfihSs2t3sL0mKpgjMAwYjtC9Yg92gHnL9Uwoxlf4s/XpIBVxPZUZvzdM9xtmt81UEp27lUUy9UmwCtftt6RI+ynrtOlbq4BqWHDOLVapNWp3M9rJ4KuVfsyPDR5k9+i3O5XRKYXDpTRURQqqLADgBMsRPVMoiIgCIiAJX9oUclW/IEP5E3PtB9Qlgkdtan6Ld5B89fwt5zLxmPXifw3J43TKxiq/RbRseFTIfMnLf3Sw4rCrVptTcXBFj3HiCO/gZTd+yVr0XHEqfeCJcMLiwyJUHy1UjtuRe3v8AVPHht6+Jol2ZB7v4z4lW+K1SAjHqE6AE6Aj6J0HcbS1bU/NN5fpCQe9W71PH4c02JVhqjjRkbh5jiCDoRcSI2FiMfQpjC4kdOFIy1hfPlVgQKi2JY2Fr6eJ4nfi4hRg4Sf8ARU46naMu0dnKUZxpUVw6MNDcU6YIv2HIPAgHlIjbeLqY2pSpH5KhqluQAvcjkSDcj6S9kmtqVq2Sy0ibnqr6TMTYDMPkqLa31PDvGfZ2xugosXOaq+rt7co7tSb8yTMEJy0tdi/ZHM9vWWoRwAmPZjh6VakD1mCOv0uhzlk8SrFh9S3MTb3mogKWtdmqFB3BFVjbsJLjXsXvMhcGCDmpkZ1OdCpBOZTcAi+vAa8jbWxM0w3iGW3dTE9HhcSzarkyW7WqdVR6zfylh3drra19edtbeNuEpNWuDQSlS0FXEPVUXsFRaFNlBPIAYgi/LLflLXu1iVoILBit+tUtpf5x5277TPlVHVuXUUw6+48ZXMVXrYGoXprmQ6vT7QOa945HwB0sVslA9Y24EBvM3B9094vCJVXK4uPUR3gjUHvERTTU49UVX2fQrtbbFLaaImHbjcuSOvTtyse/mNCBxMsWzKdOirIqqguX0AUHNxY9+lie7vldO7lWk+ei6NrfrizebKLMe+wM1trttJhlUYdV5sxLHyAEtjxMo5HJrc68aapM198d9ajE4XAekdKuJNwlMHlT5u57R5a6iiVnBsgYlVNyTxd/nNbTwA0HAdp2dq0XS4eoXJ46ZRrxsB/JkUatgT2CWSySyu2TjBQ6EgtcA2vr2cTPZqBiuul/bNVwqsb8jlvxJI8OJOp/9T5UqixYH0SpNwQcuYCxvx1tbuzSNErN01yjZhxHDvuLWmANymtiHJci9gOJ8f598ndgGmzCi9NCrc8q5hf5Sva4YeMi/dVgjKWZXDfw18eU2ty61T/TRRxxo1GVvnKz0iCPuHzBlhwuxMy2IvyPlpcTJsrDfFsSnS08wGbo6gHWGb0lHzgbAleN1BHOQ5q3vwca8HSsMeov1R7pEb07X+L0bL+cfRABci5AzAczcgAcyQJ4Xb9NUUDWwAL/ACRYW1I4fay+M51vxvclRjSw5zHUPW8iCtLyJGbgASF4kz0p8SpRrH1/Bmjjd7kVtTaBqEUVPVU3Yg3DProG+UFuRf5RLHgRNZKuthbS5vzJPf2aTSweitxBt2eHD1iZqR6x7NB6pjapUaUWzdbA9LVpUiOqz3b/AJdHK7X7i/RL651yUP4NcPmarW5KqUE8datU/edR9mXyehwkNML8mbK7kIiJpKhERAEREATV2mhNF7cQLjxXrAeybUETklaoI5Vvpi0qjDlbMVUMRyvf0T5qZZt2sQGopl1AzFfBrML+GZh5Sr737LShiGRBZGCVAOy5KkDuup9c3/g6xBana2gsL+u4/m3HnPCyY3DZ+TZs4l4otfXw9eoPunnE0FYdZQZ8TqkkkAG2pNuE8tjaZ4OCfo9b3Q6caZX32PdCkq8BaeMeOoZ8Sv8ARb1W98w4/ENlNqZPmg/GRtKNIdzk+9T/AJxTyOdfEjo29d6Z+xKxTqFbMDYjUEcrS3bzbPNRybkdwR39wtIRNiv2VT/dPLsc1pLWjSwzEFRmawDWHVsA3pD0uBsL/wAidU3ZQNTyngRY+YnPKWx6gIOSoPFCPeJatlYyrSAHS00+urn3LKszsklsX7Y/oWJ6ygKe3qgD26nzkjKVR24/PG0fKi/7Jsjbn/GL/wBB/wB2RjNJFTg2WtpG7S9EyFO3h/vY/wCi/wC7NDHbdBH9Kv8A3bD3rIzmmdjB2VTeg9Yyth5Ydq16TnW7+DhfYVkUaFPkrj+8Q/qy/HJKJY0KIzEHsUDztb12A9c94qjwGuoN7W4aaakafsnulRI4EDxt+CzaUE6M6eQYn3TrkKNbZuGvUGrEk31C27L3BMu2H2brSYcbkX8VNvbITZdDDq1+myn6SOB61Uy54LG0MoXpcO3D+tCm44EBgDeU5G5PYXSJjCYEKoA5C3qmatg1YWYAjsOsYbGhuC3+q9Jvc02Hr24q/wB0n3XnVjVb/gpcnZSN6NjUgDUyXa3Ekn1Am05Y5HSai4zC47RfhOx72V1akwF724Wa/qtONvpUIbQ34HQ6cNJPh6VpFrba3N0vZRxJve/zjqT7Z7oJYqBre9zxv/Os8VqWiDWTm7+zRXxtOib2LAG3GyjMbeSmW1ey7i63Oq7mYIUsGgHyruT2kn9gEnJiw1BaaKiCyqAqjsAFgJlnrQjpikYm7diIiSOCIiAIiIAiIgHNfhQxA6emqKzv0bB8tuqCwKEkkC/paXvz7JqboijTp9GHxDudWp0bm1/nOAtvWJn+EbY1QYpa+e9CoMrJcgiqqdU6cVKqfMd8r/wb4v4uKjvYLUqVBTBJBfoVzPl0I0F+Nr2NuE8niotyk2aoP3UdKwmFGhGFAPzqrhn9fWPtkgQ9vkDyY/jMWAx9Ooiujgq6qy68m4acuzymbF11QXbmQo0JJJNgABMqiq6nG9zyqt863gAPfefMQhC+m3r/AGCai7Wp5UYZm6RsigA3zDNfN80DK1ydNJsLX6SkHAtmF7cfaOPjOaVQ3Obb17YxKOQteoB3G3ulXbG4hzrWrMT/APpU1vy4ywb2WNUga2OvYP2yM1o4epWX0z0dOm2nV6Z+jLgH5Q1t/GXYoppJLdljdKyP6CozdYOxHHNdiO3jqJ0TdGgmUdRfuicLw206lKoaiO4ZSSLsWBtxDgmzA987ju+9mCU+rex11y3QOVA52vYXlvHcLPAlqrfx8CGLKpp0XmjTUD0R6hNgCaFLOo1e47WC6eOUDTv5TZp4gHjoeHG4Nuw8D7+6ZoNUQZlaRe0/RM941qoLNmygWIvlykaXB534yM25VdrBTlGXMRYZmOllF+HP1SGRk4Lc5hvewznQSvYaxPASV3uLM9gDf3d55CROzaRBF766g8AdSOqflcDqNNOc2Y1UCcupYMBh1+aPUJv4ikii+VQeVhr7NRPOzqVuJ/8AdryC33LBxTUuF6I1msSMzEsALjkoTh9KMGCXEZVjj13+25yc1jjqZaNi7PJqDpFbragnML94POX/AA27eFYDNSv9uoPc04z8F+12WuaRZujKkspYsMy6h1B9HTMD4Dtnb6OMqJRFY0xktmZQSXVPndjEDUqO+xPOvPw7xZdEiPM1RtGCruTg2+QR4Nf9K8wHcmmv5vEYhPBlt/hAlopVAQCDcEXHeDwI7RI9NsoawolXBY1FVrdUmkesLg6HmL8bS3lwor1yKhtzZ2Lw1Mt8ceovzXDe9mYeyUVduuzEGpVHLKRTen9wi3snSt6doU6tJ1QkkBtbGxynK1jwNj7xOQU16x8ZRBW3ZensTdTFKwF1o5uRCtRbyUHoz92Tfwe1QNor0pVSVYJYnViAAO42zSCxGH6MoXKgsWULxJKWzg9lsw90l9wdkNXxoYECnSIqMOdw10CdlyDfuluFPUmjk+jOyxET1zGIiIAiIgCIiAIiIBT/AISj+Qpd1Qm2tzak9wNOOs5tVoE1MCq9QUyazg3/ADYLu5JPIkMZ0D4Va6pQpFrek5uTYABdT7h5mUL4NcWVFX40pWmSwRqidbo2OYorMM2QsL2EwZsksU5yW+1fUvjFSikdF3Po2woXILMocE+g4caC3ECwAta3rkniQFTK3XGmVPSa9xYA8wDbU8BqTaRdbe/B0dC9rDgFIFuXpWExUd9aNXSnTqsO0ZCP8DMfZPNi049Sck7uiVwuEORM566sahynTM2bMBcar1yNe46T5tNXZRTpgqp9N9FyqPkIOJY8L2sBc34A6ibYqHhh6vmtUf8Aa/Gfa+0sQRpQP2g38Jy68/RjuUTeSkEcga6km+tyTqSZFmoj0npVgWpuADlsCpU5lZeGoPv4yw7bdybtTpDycn1FjIIuvMkeFFiP0DJwnt0ZbVkBs/ZGHSuXrVXqhbFVyAZyOHSEWuO0aXnRt2rPctxLFieBuTqdOErlZ6ZdcjMFt1gabXJ7jbSSGDoVT6HTn6oqL7p3PmnlScr+fYRhGPSjp2EQBQo4Wt2+/jPYo29GwHNbaeVuH8JQ8JhMZfX41b6z/iwkslCvbVcV94n/ALwlKm1/F/b9kHBeSwPg7m7WtpZQLC/ax4nlpoPGaG2lBUmwuO0X9k0RQq31TEkfz/xE18VTFtaWIH1lLe6tIyk3/F/b9iKV9TnW9lFnqEBbLf0bkj3TR2dgGuCbnxvzN9OyXjEYrDIeuzDuKFfb0sjMVjcMw6lRr9xYe4mXxzSUa0v7fsnSvqesHheB1/CYtv7NFZVBRiVuA6EZlDcQQ2jLoDbj4WmuHX51f7Jrn3CZVZyw6OpUUc+kTENf16e2Sx5J45qcLTXcSgpKnTR73C3QWjX6UlmbUXYBdDxGUdul73nW9m4TILZ2YAWAYggDs0GvZc62lPwFfEadCKLaC+Yi9+ema8llxu0x/UUj4X/B458sk9c7b/r9FcoUqVEl8SegymkGejYq1K+tPrFlejcjQXYFOzLb0Qp2RQD1FcAqFJY6ZczEAXItckAcT28+UVT2tjh6WFU+HSD8DNsbYqgdbDsPtKP0rS/n411v6P8ARXol6ZBbZpKi1KdWyWB6N72D073Crc2zi5BHHnqOHMMVUV2tayDQL3E8e0k8yfYLW6/j8Y+IRkFPKTpq9/0VM51tXdavSJY5AO8t+KTPGcNT3Lop1uVjePE1GaimHI63SoQL3LmtdWuQOubrqfbOjfBJUtXrqRqyK33XYeo5pTxQqJZmAC9azDIwuQVY5lvYkEjWWb4KA3x2pp1RRbX7aW8vCb8eXUscUv8AG9/N/ornGtT8nWoiJvMwiIgCIiAIiIAnirUCqWJsACSewAXJnuY8RRDoyNwYFT4EWPvgHHt4ds1No4lFqYcLRRiaWdwNDb8pUtc3NhZRw7Za8LsjAhRnrK3aOkyr90MAfO8pG9e721cO5CIa1EejUprna306Y6wNuOhHfINMNVLBlrtmHpUqiqQD3qMp9c8nNCbeqZrjTVROxYZ9mUPQOGT6oQH1gT3V3qwK8cSvlm/ATlnxKi5HT4RGPz6NetQP3LkD1zI+CwnyfjyeJpV1/wAQJmbmeU/X9NneXudIG+OC5VC3gjn8Jgxm8+DcWZapHcrD8ROafFQPRq3/AOZQVD61yzew2zAw/pOFB7Caw/RYyLkvS/dE+WkTGKGzHN+ixN+5re9pg/0Zgm9GhXPjUQfgZrJu9VPo18Gf76sP0qc26W7ePHovh2+rXH4pHvv/ABa+v+zvu97PI2NTGqUWHjVT9yZx8ZT0HZf7wH9WeH2RtRf6tT4V6H4gTRxj46n6dB/s1KD/AKJlbWW93E6nA3m2tjF/r2+8P3Z8G28X/vD/AHh+7K9UxdVuNHE/cUz1Q6W9+ixHnTH7ZKprq19DvueCyJj8a3Cu/wB7/wAZ7NHGtxquftn92Ydm7Yen/Y6rfZt+Mmae81Tls+rI3P0iLpdEQNfYNZvSN/Fif1ZpVdhsvED1/wAJa6m81b/66r6xIrH7aruLfEWXxZfxad15OzCruivth8vZ7P2TIlBjwI9kwYihiHNxQqeT0R7yZubMweKB/obN9fEU1H+FDJOUq6ol7p9XZNU6hR4g/wAZs0zjafotVHg7ftlhw9bHqOrg8Gv167Mf8KT6cZtLkuzl8sS/6KzqlLu4+vmVtrwQ1Pa20hwqVvMBvepmwm8m0l4s58aS/gokoH2mR+ew6/8ALwtd/a4E16mD2ux/pbgfRw9FPaxJEs5jj/L6X/4RpPsaVberFMLVEosPp0r/AKwkTiNpYnNmpOtLup9Iq/dzEeybW2NnV1H+tYzFsPmjEUqd/s0xeQNDBYcG9Og4Pz3q1anmRnX3SPMT3pv5fuiSj/RMNtvFuhSstCsv00Vv2GaW7u06mBrmuiKFIKtT1tlJBOUkm2oEj8WuW7DEPm5IoUKfXf8AGbm7uwtpYpxmw1kv+cqKaSgdvW1byBmjEpPeJyWlbM7fszGrXpJVS+V1DC/EdoPeDcTamlsbZ4w9CnRBuEW1+08SbctSdJuz1ldbmNiIidOCIiAIiIAiIgCYcRhUqCzorjsYBh7ZmiARNXdnBtxw9MfVXJ+jaaj7l4I8KbL4VKn4sZYYkHjg+qRLU/JVKu4OFPBqw8GX8VM1n+DujyrVfPIfcBLpEjyMfg7zJeShv8G6csQfOmD+MxH4NzyxI/6X/nOgxOPh8b7HebLyc8/+O6o4YlfuEfrT4dwcRyxK/wCITokSPsuLwd50zm7fB9if9unrcfqwu4OKHCuv36n7s6RE57Ji8DnTKBS3Tx68MRT8yT76ZmwN39pf7xS9S/5Mu8TnsWHwOdIpB3e2kf7TT9S/5UxPuljm9LEUz94foqJfIj2PD4HOkUBNw6l7t8Xb63Tn9cSQwu61an6PxNe/oXJ9ZqS3xO+yYvBzmyIFNm4wf2iiPCg3+bMn+jMVzxYH1aKj3sZNRJLhsS7fkjrZBNsKs3pY2v8AZWkvuWYX3QRvTxGKfuaoLeoLLHE77Pj8DXIrdPcbAjjTZvGpUH6JE26W6uCX+z0z9YZ/07yZiTWOC6JDVLya+GwVKnpTpon1VVfcJsREmREREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAEREAREQBERAP/9k=")
                .googleAT("")
                .build();
        userService.joinMember(command1);
        JoinUserCommand command2 = JoinUserCommand.builder()
                .nickname("Queen")
                .name("Queen")
                .email("Queen@Queen.com")
                .profileImage("https://people.com/thmb/YIww0u4m8icR9vnhychZVxZecFs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc():focal(701x0:703x2)/freddie-mercury-3-f480f4ef58b145c7871f123c8b2d3aae.jpg")
                .googleAT("")
                .build();
        userService.joinMember(command2);
        JoinUserCommand command3 = JoinUserCommand.builder()
                .nickname("Pawn")
                .name("Pawn")
                .email("Pawn@Pawn.com")
                .profileImage("data:image/jpeg;base64,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")
                .googleAT("")
                .build();
        userService.joinMember(command3);
    }

    /**
     * 기업 회원 가입
     */
    public void createDummyCompanyUsers(){
        log.info("createDummyCompanyUsers");
        JoinCompanyUserCommand command1 = JoinCompanyUserCommand.builder()
                .email("ssafyCompany@ssafy.com")
                .name("ssafyCompany")
                .phone("010-0101-0101")
                .password("1234qwer")
                .company("ssafy")
                .comNo("1111")
                .department("B206")
                .build();
        userService.JoinCompanyUser(command1);
        JoinCompanyUserCommand command2 = JoinCompanyUserCommand.builder()
                .email("ssammu@ssammu.com")
                .name("ssammu")
                .phone("010-1234-1234")
                .password("1234qwer")
                .company("ssammu")
                .comNo("1234")
                .department("meet")
                .build();
        userService.JoinCompanyUser(command2);
    }

    /**
     * 광고 추가
     */
    public void createDummyAdvert(){

        List<Long> contentList1 = new ArrayList<>();
        contentList1.add(1L);
        contentList1.add(2L);
        contentList1.add(3L);

        List<String> advertVideoUrlList1 = new ArrayList<>();
        advertVideoUrlList1.add("https://ssafy-stad.s3.ap-northeast-2.amazonaws.com/AdvertVideo/71cc0506891f4de4aa5bc28389e971c9videoList");

        AddAdvertRequestCommand command1 = AddAdvertRequestCommand.builder()
                .userId(5L)
                .title("유산균 김치")
                .description("발효된 Super Food 김치")
                .startDate(LocalDateTime.parse("2024-04-25T00:00:00"))
                .endDate(LocalDateTime.parse("2025-04-25T00:00:00"))
                .type("NOTPRODUCT")
                .directVideoUrl("")
                .bannerImgUrl("https://contents.codetree.ai/homepage/images/company/SSAFY_logo.png")
                .selectedContentList(contentList1)
                .advertVideoUrlList(advertVideoUrlList1)
                .category("개발")
                .build();

        advertService.addAdvert(command1);

        List<Long> contentList2 = new ArrayList<>();
        contentList2.add(1L);
        contentList2.add(3L);

        List<String> advertVideoUrlList2 = new ArrayList<>();
        advertVideoUrlList2.add("https://ssafy-stad.s3.ap-northeast-2.amazonaws.com/AdvertVideo/71cc0506891f4de4aa5bc28389e971c9videoList");

        AddAdvertRequestCommand command2 = AddAdvertRequestCommand.builder()
                .userId(4L)
                .title("싸피 12기")
                .description("개발자로 취업할 수 있는 절호의 기회")
                .startDate(LocalDateTime.parse("2024-04-25T00:00:00"))
                .endDate(LocalDateTime.parse("2025-04-25T00:00:00"))
                .type("product")
                .directVideoUrl("")
                .bannerImgUrl("https://img.khan.co.kr/lady/r/1100xX/2023/03/08/news-p.v1.20230308.9abb9311c8ee43c6b181dd72e08fa534.png")
                .selectedContentList(contentList2)
                .advertVideoUrlList(advertVideoUrlList2)
                .category("푸드")
                .build();

        advertService.addAdvert(command2);
    }

}
