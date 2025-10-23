package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.data.api.FirebaseService

class DollarRemoteDataSource(
    private val firebaseService: FirebaseService
) {

    suspend fun setDollar(value: String) {
        firebaseService.updateMessage(
            "ya29.c.c0ASRK0GZfcYciGEZTlVxsQpUxfE0_Hx5AEgKHbe7s83FsKuxfMuMa2aWjDg9NgFRsUw5VJwQunHLBYJXQrt46LEjSi0N7AKlAy3jHiVqJRZmOHLlmOefy2-kGa4iHQmbETjwob1eIZQnRj9fsutqjZC8QrzXiCWQ0YNEyW8LyWLUy1dN1YHoqcoyBOOcvxAPvWyoGPiIxQ24Ik3EOcF4C9uTxMsoVrqrEO5z7VGkeQ7nmK-dAwbNGVA5qfBwSVBApFXFMd6wTxXCxin8WDWIcUL3Od9dYEyFqN92jeYtsSGCj3JfFEwt7DQZsg3ecUdQiLckfir2RcDi34IGZbDiqDmgIWPU9MoNHTrFZIFKvepURcdvsPnSZ2xwL384P8BsjXi-r8t9QevhBBceSzZBSv3YVYMYqdIe8Q-auf6Q6SWZyrOjM_1MhsIbv5zsMw1f2X-kqqdvgxXjnYRFjJ6qQalzqwegytOIMbciyBpZeO9cbJowssj5hRuJmSxtt5_j2n-W58xxy6dtFm91w0-2obO463ad5hF6luhjIF-0FQ4zJxOm3ygZesvon2B_Zdq_nQ5FcdO8oQOkde59roWpSZax_v7iQb0RUjmnVJ1jfdMVkfIUYM-yrXk3O0_0JcjbMu_7cayc1r0fMUUjmc9rmxUy0BzevYzk8hkBRYgX6m-b6Vm929tn9y1bhQw6lVfO7OIx0Qfbtmq4XzRRaaU_Jv9M214siXoy5iFjltnuJJ1q1l3WXeVt25yFs1lx9bQhe0YMaaxcRpqi8Sq76Yv27qBUqMvJzmj4Y-Zpg6_YfXexORanqQMSmu04R7l8rMznbz0cXdzi4VfhnMORj7ooqqV2acfRgriuly6tcoUd7IlRtj9ivJf5uhZRpRxIXakURUB6wFxWbh42aSYM70s5vUneiwUrs-X7zeJB-0omwnB4ehoki6fO8ucrZpucfnw_sgctpa62Wb-e1Ff-erBV3ZXIpXWX0RkWy3utq9FUBXBpgr1gmxRoYb2U",
            mapOf(
                "message" to value
            )
        )
    }
}